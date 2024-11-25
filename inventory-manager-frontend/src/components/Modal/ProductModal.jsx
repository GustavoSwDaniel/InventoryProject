import { useEffect, useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { getProductById, updateProducts } from "../../slices/ProductSlice";
import { useDispatch, useSelector } from "react-redux";

const ProductModal = (props) => {
  const [product, setProduct] = useState({
    id: '',
    name: '',
    description: '',
    value: 0
  });
  const [change, setChange] = useState(false);

  const dispatch = useDispatch();
  const { status, error, item } = useSelector((state) => state.product);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct({ ...product, [name]: value });
  };
  
  useEffect(() => {
    if (props.showModal === true) {
      dispatch(getProductById(props.productId));
    }
  }, [props.showModal]);
  
  useEffect(() => {
    if (item) {
      setProduct({
        id: item.id || '',
        name: item.name || '',
        description: item.description || '',
        value: item.value || ''
      });
    }
  }, [item]);



  const updateProduct = () => {
    console.log("alooo")
    dispatch(updateProducts({ id: props.productId, data: product }))
  };

  return (
    <>
      {props.showModal ? (
        <>
        <ToastContainer/>
          <div className="justify-center items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">
            <div className="relative w-auto my-6 mx-auto max-w-3xl">
              <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none">
                <div className="flex items-start justify-between p-5 border-b border-solid border-blueGray-200 rounded-t">
                  <h3 className="text-3xl font-semibold">Producto</h3>
                  <button
                    className="p-1 ml-auto bg-transparent border-0 text-black opacity-5 float-right text-3xl leading-none font-semibold outline-none focus:outline-none"
                    onClick={() => props.setShowModal(false)}
                  >
                    <span className="bg-transparent text-black opacity-5 h-6 w-6 text-2xl block outline-none focus:outline-none">
                      ×
                    </span>
                  </button>
                </div>
                {/*body*/}
                <form className="relative p-6 flex-auto" onSubmit={(e) => { e.preventDefault(); updateProduct(); }}>
                  <div className="grid grid-cols-2 gap-4">
                  <div className="flex flex-col">
                      <label className="text-black">ID</label>
                      <input
                        type="text"
                        name="id"
                        value={product.id}
                        onChange={handleChange}
                        className="border border-gray-300 p-2 rounded"
                        disabled
                      />
                    </div>
                    <div className="flex flex-col">
                      <label className="text-black">Nome</label>
                      <input
                        type="text"
                        name="name"
                        value={product.name}
                        onChange={handleChange}
                        className="border border-gray-300 p-2 rounded"
                        required
                      />
                    </div>
                    <div className="flex flex-col">
                      <label className="text-black">Descriação</label>
                      <input
                        type="text"
                        name="description"
                        value={product.description}
                        onChange={handleChange}
                        className="border border-gray-300 p-2 rounded"
                        required
                      />
                    </div>
                    <div className="flex flex-col">
                      <label className="text-black">Preço</label>
                      <input
                        type="text"
                        name="value"
                        value={product.value}
                        onChange={handleChange}
                        className="border border-gray-300 p-2 rounded"
                        required
                      />
                    </div>
                    
                  </div>
                {/*footer*/}
                <div className="flex items-center justify-end p-6 border-t border-solid border-blueGray-200 rounded-b">
                  <button
                    className="text-red-500  background-transparent font-bold uppercase px-6 py-2 text-sm outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                    type="button"
                    onClick={() => props.setShowModal(false)}
                  >
                    Fechar
                  </button>
                  <button
                    className="p-2 flex items-center gap-3 bg-orange-500 text-white font-semibold rounded-lg hover:bg-orange-600 transition"
                    type="submit"
                    >
                    Salvar
                  </button>
                </div>
                </form>
              </div>
            </div>
          </div>
          <div className="opacity-25 fixed inset-0 z-40 bg-black"></div>
        </>
      ) : null}
    </>
  );
};

export default ProductModal;
