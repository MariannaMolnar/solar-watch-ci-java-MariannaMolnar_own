package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.InvalidCityException;
import com.codecool.solarwatch.exception.InvalidCountryCodeException;
import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.payload.CityDTO;
import com.codecool.solarwatch.model.payload.NewCityDTO;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.service.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class CityService {
  //private final RestTemplate restTemplate;
  private static final Logger LOGGER = LoggerFactory.getLogger(CityService.class);
  private final CityRepository cityRepository;
  private final Mapper mapper;
  @Value("${solarwatch.geocode.api.key}")
  private String API_KEY;
  private final WebClient webClient;

  @Autowired
  public CityService(WebClient webClient, CityRepository cityRepository, Mapper mapper) {
    this.webClient = webClient;
    this.cityRepository = cityRepository;
    this.mapper = mapper;
  }

  public CityDTO getCity(String cityName, String country) {
    String formattedCityName = cityName.substring(0, 1).toUpperCase() + cityName.substring(1);
    String formattedCountry = country.toUpperCase();
    Optional<City> cityInDB = getCityFromDatabase(formattedCityName, formattedCountry);

    if (cityInDB.isPresent()) {
      return mapper.cityToCityDTO(cityInDB.get());
    } else {
      Optional<NewCityDTO> geocodeResponse = getGeocodeResponse(cityName, country);

      if (geocodeResponse.isEmpty()) {
        throw new InvalidCityException("City with name" + cityName + "does not exist");
      }
      City newCity = mapper.newCityDTOToCity(geocodeResponse.get());

      cityRepository.save(newCity);
      return mapper.cityToCityDTO(newCity);
    }
  }

  // get city geocoding from API
  private Optional<NewCityDTO> getGeocodeResponse(String city, String countryCode) {
    if (countryCode == null || countryCode.length() != 2) {
      throw new InvalidCountryCodeException();
    }
    String cityAndCountryCode = city + ',' + countryCode;
    String geocodeUrl =
            format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s",
                    cityAndCountryCode, API_KEY);

    NewCityDTO[] geocodeResponse = webClient
            .get()
                    .uri(geocodeUrl)
                            .retrieve()
                                    .bodyToMono(NewCityDTO[].class)
                                            .block();
    LOGGER.info("Response from Geocoding API: {}", geocodeResponse);

    if (geocodeResponse.length == 0) {
      return Optional.empty();
    }
    return Optional.of(geocodeResponse[0]);
  }

  //find city in database
  private Optional<City> getCityFromDatabase(String city, String country) {
    return cityRepository.findByNameAndCountry(city, country);
  }

  public void createCity(NewCityDTO city) {
    Optional<City> cityInDB = getCityFromDatabase(city.name(), city.country());

    if (cityInDB.isPresent()) {
      throw new IllegalArgumentException(format("city %s already exists", city.name()));
    }

    City newCity = mapper.newCityDTOToCity(city);
    cityRepository.save(newCity);
  }

  public void updateCity(CityDTO city) {
    Optional<City> cityInDB = cityRepository.findById(city.id());

    if (cityInDB.isEmpty()) {
      throw new IllegalArgumentException(format("city %s doesn't exist", city.name()));
    }

    City updatedCity = mapper.cityDTOToCity(city);
    cityRepository.save(updatedCity);
  }

  public void deleteCity(long id) {
    Optional<City> cityInDB = cityRepository.findById(id);
    if (cityInDB.isEmpty()) {
      throw new IllegalArgumentException(format("city %s doesn't exist", id));
    }
    cityRepository.deleteById(id);
  }
}
