import logo from "../../../../assets/sun-logo2.png";

function SolarWatchLogo({ h = 10 }) {
    return (
        <img
            className={`mx-auto w-auto h-${h}`}
            src={logo}
            alt="SolarWatch Logo"
        />
    );
}

export default SolarWatchLogo;