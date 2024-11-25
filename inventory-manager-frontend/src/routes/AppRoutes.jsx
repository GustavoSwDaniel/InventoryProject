import { Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import ProductsPage from "../pages/Producs";
import Home from "../pages/Home/Index";
import RegisterPage from "../pages/Register";
import PrivateRoute from "./PrivateRoute";


const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<RegisterPage />} />

      <Route path="/products" element={<PrivateRoute element={<ProductsPage />} />} />
      <Route path="/" element={<PrivateRoute element={<Home />} />} />
    </Routes>
  );
};

export default AppRoutes;
