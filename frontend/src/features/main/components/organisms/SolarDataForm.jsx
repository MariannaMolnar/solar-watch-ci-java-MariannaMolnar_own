import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import InputField from "../atoms/InputField";

import Button from "../atoms/Button";

function SolarDataForm({ city, setCity, country, setCountry, date, setDate, handleDataRequest }) {



    //const navigate = useNavigate();

    return (
        <div>
            <h1 className="font-bold text-3xl mb-5"> Request Solar Information</h1>
            <form onSubmit={handleDataRequest} className="flex flex-col items-center">
                <div>
                    <InputField
                        label="City"
                        type="text"
                        value={city}
                        onChange={(e) => setCity(e.target.value)}
                        required
                    />
                    <InputField
                        label="Country"
                        type="text"
                        value={country}
                        onChange={(e) => setCountry(e.target.value)}
                        required
                    />
                    <InputField
                        label="Date"
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                    />

                    <Button type="submit"
                        className="flex w-full justify-center rounded-md px-3 py-1.5 text-sm/6 font-semibold text-black shadow-sm focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                        Submit
                    </Button>

                </div>
            </form>
        </div>
    )
}

export default SolarDataForm;