import { useState } from "react";
import Cookies from "js-cookie";

const Sidebar = () => {
  const [submenuVisible, setSubmenuVisible] = useState(false);
  const position = Cookies.get("position");

  const logout = () => {
    Cookies.remove("token");
    Cookies.remove("position");
    window.location.href = "/login";
  };

  const toggleSubmenu = (e) => {
    e.preventDefault();
    setSubmenuVisible(!submenuVisible);
  };

  return (
    <div>
      <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
      />

      <div
        className="flex flex-col top-14 left-0 w-14 hover:w-64 md:w-64 bg-white h-full text-gray-600 transition-all duration-300 border-none z-10 sidebar"
        style={sidebarStyles}
      >
        <div className="flex flex-col justify-between flex-grow">
          <ul className="flex flex-col py-4 space-y-1">
              <li>
                <a
                  href="/products"
                  className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-purple-400 text-gray-600 hover:text-gray-800 border-l-4 border-transparent"
                >
                  {/* Icono Home de Font Awesome */}
                  <span className="inline-flex justify-center items-center ml-4">
                    <i className="fa-solid fa-bag-shopping"></i>
                  </span>
                  <span className="ml-2 text-sm tracking-wide truncate">
                    Produtos
                  </span>
                </a>
              </li>
            <li>
              <a
                href="#"
                className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-purple-400 text-gray-600 hover:text-gray-800 border-l-4 border-transparent"
                onClick={logout}
              >
                {/* Icono Sign Out de Font Awesome */}
                <span className="inline-flex justify-center items-center ml-4">
                  <i className="fas fa-sign-out-alt"></i>
                </span>
                <span className="ml-2 text-sm tracking-wide truncate">
                  Sair
                </span>
              </a>
            </li>
          </ul>
        </div>
      </div>
      {/* ./Sidebar */}
    </div>
  );
};

const sidebarStyles = {
  ".sidebar li:hover a::before": {
    content: '""',
    position: "absolute",
    top: 0,
    left: 0,
    height: "100%",
    width: "4px",
    background: "linear-gradient(to bottom, #00FFFF, #008080)",
  },
  ".sidebar li:hover a i, .sidebar li:hover a span": {
    color: "white",
  },
};

export default Sidebar;
