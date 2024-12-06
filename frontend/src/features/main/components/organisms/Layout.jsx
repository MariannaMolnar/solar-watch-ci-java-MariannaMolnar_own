import { Outlet } from "react-router-dom";
//import ReportButton from "../../../bugreport/ReportButton";
import Navbar from "./Navbar.jsx";
import Divider from "../atoms/Divider.jsx";

const Layout = () => {
  return (
<div className="relative min-h-screen">
  <div className="bg-background-2 absolute inset-0 bg-cover bg-center opacity-30 z-0">

  </div>



    <div className="relative z-10">
      <Navbar />
      <div className="flex-grow w-8/12 mx-auto">
        <Divider />
        <Outlet />
      </div>
    </div>
    </div>
  );
};

export default Layout;