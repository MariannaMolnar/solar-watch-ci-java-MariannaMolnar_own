import SolarDataForm from "../main/components/organisms/SolarDataForm";
import { useState } from "react";
import Loading from "../main/components/atoms/Loading";

function SolarWatch() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [city, setCity] = useState('');
    const [country, setCountry] = useState('');
    const [date, setDate] = useState('');
    const [solarInfo, setSolarInfo] = useState(null);
    const [loading, setLoading] = useState(false);

    async function handleDataRequest(e) {
        e.preventDefault();
        setLoading(true);

        const token = localStorage.getItem("jwt");

        let response;

        try {

            if (date !== '') {
                console.log(localStorage.getItem("jwt"))
                response = await fetch(`/api/sunrise-sunset?city=${city}&country=${country}&date=${date}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
            } else {

                response = await fetch(`/api/sunrise-sunset/current?city=${city}&country=${country}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
            }

            if (!response.ok) {
                console.error('Error: ', response.status, await response.text());
                setLoading(false);
                return;
            }

            const data = await response.json();
            setSolarInfo(data);

        } catch (error) {
            console.error("Error fetching information:", error);
        } finally {
            setLoading(false);
        }
    }
    return (
        <div>
            <SolarDataForm
                city={city}
                setCity={setCity}
                country={country}
                setCountry={setCountry}
                date={date}
                setDate={setDate}
                handleDataRequest={handleDataRequest}
            />
            {loading ? (
                        <Loading />
                    ) : (
                        solarInfo && (
                            <div>
                                <h3>Solar information for {solarInfo.cityName} on {solarInfo.date}</h3>
                                <h4>Sunrise: {solarInfo.sunrise}</h4>
                                <h4>Sunset: {solarInfo.sunset}</h4>
                            </div>
                        )
                    )}
        </div>
    )
}

export default SolarWatch;