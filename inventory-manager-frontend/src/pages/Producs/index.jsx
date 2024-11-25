import { useDispatch, useSelector } from 'react-redux';
import { deleteProductById, getProducts } from '../../slices/ProductSlice'; // Thunk para pegar produtos
import { useEffect, useState } from 'react';
import Sidebar from "../../components/sidebar";
import Header from '../../components/Header/Header';
import ProductModal from '../../components/Modal/ProductModal';
import CreateProductModal from '../../components/Modal/CreateProductModal/CreateProductModal';
import { FiTrash2 } from 'react-icons/fi'; // Importando ícone de lixeira

const ProductsPage = () => {
  const dispatch = useDispatch();
  const { content, totalPages, status, error } = useSelector(
    (state) => state.product
  );
  const [showModalProduct, setShowModalProduct] = useState(false);
  const [showCreateModalProduct, setShowCreateModalProduct] = useState(false);
  const [productId, setProductId] = useState(null);

  const [params, setParams] = useState({
    page: 0,
    size: 12,
  });

  useEffect(() => {
    dispatch(getProducts(params));
  }, [dispatch, params]);

  const handlePageChange = (page) => {
    setParams({ ...params, page });
  };

  const openModal = async (e, id) => {
    e.preventDefault();
    await setProductId(id);
    setShowModalProduct(true);
  };

  const deleteProduct = (e, productId) => {
    e.stopPropagation();
    e.preventDefault();
    if (window.confirm('Tem certeza que deseja excluir este produto?')) {
      dispatch(deleteProductById({ id: productId }))
        .then(() => {
          dispatch(getProducts(params));
        });
    }
  };


  return (
    <div className="w-full h-full flex flex-col">
      <Header />
      <div className="w-full h-screen flex">
        <Sidebar />
        <div className="flex flex-col h-screen w-full">
          <div className="p-6">
            <ProductModal
              showModal={showModalProduct}
              setShowModal={setShowModalProduct}
              productId={productId}
            />
            <CreateProductModal
              showModal={showCreateModalProduct}
              setShowModal={setShowCreateModalProduct}
              params={params}
            />
            <div className="flex items-center mb-6">
              <h1 className="text-2xl mr-2 font-semibold">Lista de Produtos</h1>
              <button className="bg-orange-500 text-white px-4 py-2 rounded-lg font-semibold hover:bg-orange-600 transition"
                onClick={() => setShowCreateModalProduct(true)}>
                Cadastrar produto
              </button>
            </div>

            <table className="min-w-full table-auto border-collapse border border-gray-200">
              <thead>
                <tr className="bg-gray-100 text-gray-600">
                  <th className="px-4 py-2 text-left">ID</th>
                  <th className="px-4 py-2 text-left">Nome</th>
                  <th className="px-4 py-2 text-left">Descrição</th>
                  <th className="px-4 py-2 text-left">Preço</th>
                  <th className="px-4 py-2 text-left">Ações</th>
                </tr>
              </thead>
              <tbody>
                {(content && Array.isArray(content) ? content : []).map((product) => (
                  <tr
                    key={product.id}
                    className="hover:bg-gray-50 dark:border-gray-700 cursor-pointer"
                    onClick={(e) => openModal(e, product.id)}
                  >
                    <td className="px-4 py-2 border-t">{product.id}</td>
                    <td className="px-4 py-2 border-t">{product.name}</td>
                    <td className="px-4 py-2 border-t">{product.description}</td>
                    <td className="px-4 py-2 border-t">
                      {new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(product.value)}
                    </td>
                    <td className="px-4 py-2 border-t">
                      <button
                        onClick={(e) => deleteProduct(e, product.id)}
                        className="text-red-500 hover:text-red-700 z-[100]"
                      >
                        <FiTrash2 size={20} />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>

            </table>
            <div className="mt-4 flex justify-center space-x-2">
              {Array.from({ length: totalPages }).map((_, index) => (
                <button
                  key={index}
                  onClick={() => handlePageChange(index)}
                  className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none"
                >
                  {index + 1}
                </button>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductsPage;
