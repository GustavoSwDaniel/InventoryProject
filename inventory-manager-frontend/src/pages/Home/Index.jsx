import Sidebar from "../../components/sidebar";
import Navbar from "../../components/Header/Header";

const Home = () => {
  return (
    <div className="w-full">
      <Navbar />
      <div className="w-full h-full flex flex-col">
        <div className="w-full h-screen flex bg-[#d8d8d8]">
          <Sidebar />
          <div className="flex flex-col h-screen w-full items-center justify-center">
            <h1>Bem vindo</h1>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
