import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { createProduct, getProducts } from "../../../slices/ProductSlice";


const CreateProductModal = (props) => {
  const [product, setProduct] = useState({
    name: "",
    description: "",
    value: 0,
  });

  const dispatch = useDispatch();

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === 'name' || name === 'description') {
      if (value.length <= 100) {
        setProduct({ ...product, [name]: value });
      }
    } else if (name === 'value') {
      setProduct({ ...product, [name]: parseFloat(value) || 0 });  
    }
  };

  const createProducts = () => {
    dispatch(createProduct({ data: product }))
      .then(() => {
        dispatch(getProducts(props.params)); 
        props.setShowModal(false);
        setProduct({
            name: "",
            description: "",
            value: 0,
          })
      });
  };

  return (
    <>
      {props.showModal ? (
        <>
          <div className="justify-center items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">
            <div className="relative w-auto my-6 mx-auto max-w-3xl">
              <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none">
                <div className="flex items-start justify-between p-5 border-b border-solid border-blueGray-200 rounded-t">
                  <h3 className="text-3xl font-semibold">Criação de Produto</h3>
                  <button
                    className="p-1 ml-auto bg-transparent border-0 text-black float-right text-3xl leading-none font-semibold outline-none focus:outline-none"
                    onClick={() => props.setShowModal(false)}
                  >
                    <span className="text-black h-6 w-6 text-2xl block outline-none focus:outline-none">×</span>
                  </button>
                </div>
                <form className="relative p-6 flex-auto" onSubmit={(e) => { e.preventDefault(); createProducts(); }}>
                  <div className="grid grid-cols-2 gap-4">
                    <div className="flex flex-col">
                      <label className="text-black">Nome</label>
                      <input
                        type="text"
                        name="name"
                        value={product.name}
                        onChange={handleChange}
                        className="border border-gray-300 p-2 rounded"
                        maxLength="100" 
                      />
                    </div>
                    <div className="flex flex-col">
                      <label className="text-black">Descrição</label>
                      <input
                        type="text"
                        name="description"
                        value={product.description}
                        onChange={handleChange}
                        className="border border-gray-300 p-2 rounded"
                        maxLength="100" 
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
                      />
                    </div>
                  </div>
                </form>
                {/* Footer */}
                <div className="flex items-center justify-end p-6 border-t border-solid border-blueGray-200 rounded-b">
                  <button
                    className="text-red-500 background-transparent font-bold uppercase px-6 py-2 text-sm outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                    type="button"
                    onClick={() => props.setShowModal(false)}
                  >
                    Fechar
                  </button>
                  <button
                    className="p-2 flex items-center gap-3 bg-orange-500 text-white font-semibold rounded-lg hover:bg-orange-600 transition"
                    type="submit"
                    onClick={createProducts}
                  >
                    Criar Produto
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div className="opacity-25 fixed inset-0 z-40 bg-black"></div>
        </>
      ) : null}
    </>
  );
};

export default CreateProductModal;
