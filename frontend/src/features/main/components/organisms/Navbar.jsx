import { Link, useNavigate } from "react-router-dom";
import BiggerOnHover from "../atoms/BiggerOnHover";
import SolarWatchLogo from "../atoms/SolarWatchLogo";
import { useEffect, useState } from "react";
import Button from "../atoms/Button";

function Navbar() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    //const [username, setUsername] = useState('');
    //const username = localStorage.getItem("userName");

    /*const checkLogin = () => {
        if (isLoggedIn) {
            setUsername(localStorage.getItem("userName"))
            return true;
        } else {
            setUsername('');
            return false;
        }
    };*/
    const navigate = useNavigate();

    function handleLogout() {
        localStorage.clear();
        setIsLoggedIn(false)
        //setUsername('');
        navigate("/");
    }

    useEffect(() => {
        console.log(localStorage)
        const token = localStorage.getItem("jwt");
        console.log(token)
        if (token) {
            setIsLoggedIn(true);
        } else {
            setIsLoggedIn(false)
        }
    }, []);


    return (
        <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
            <div className="flex flex-1 items-center justify-between sm:items-stretch sm:justify-start">
                <div className="flex shrink-0 items-center">
                    {/* Logo */}
                    <BiggerOnHover>
                        <a href="/" className="flex items-center">
                            <h1 className="text-center">
                                {"SOLARWATCH".split("").map((char, index) => (
                                    <span key={index} className="block text-3m text-bold mx-1">
                                        {char}
                                    </span>
                                ))}
                            </h1>

                            <SolarWatchLogo />
                        </a>
                    </BiggerOnHover>
                </div>
                {/* Middle Section: GetInfo Button */}
                <div className="ml-auto">
                    <BiggerOnHover>
                        {isLoggedIn ? (
                            <Link to="/solar-watch" className="inline-block font-semibold py-2 px-4 rounded-lg shadow-md transition-transform bg-pink-300 hover:bg-buttonHover text-white">
                                Get info
                            </Link>
                        ) : (
                            <Link to="/login" className="inline-block font-semibold py-2 px-4 rounded-lg shadow-md transition-transform bg-pink-300 hover:bg-buttonHover text-white">
                                Get info
                            </Link>
                        )}
                    </BiggerOnHover>
                </div>
                {/* Right Section: Login/Logout Button */}
                <div className="ml-auto">
                    <BiggerOnHover>
                        {isLoggedIn ? (
                            <button onClick={handleLogout} className="inline-block font-semibold py-2 px-4 rounded-lg shadow-md transition-transform bg-pink-300 hover:bg-buttonHover text-white">
                                Log out
                            </button>
                        ) : (
                            <Link to="/login" className="inline-block font-semibold py-2 px-4 rounded-lg shadow-md transition-transform bg-pink-300 hover:bg-buttonHover text-white">
                                Sign In
                            </Link>
                        )}
                    </BiggerOnHover>
                </div>
            </div>
        </div>

    )
}

export default Navbar;