import { useDispatch, useSelector } from 'react-redux';
import { fetchAuditLogs } from '../../slices/AuditLogsSlice'; 
import { useEffect, useState } from 'react';
import Sidebar from "../../components/sidebar";
import Header from '../../components/Header/Header';

const AuditLogsPage = () => {
  const dispatch = useDispatch();
  const { content = [], totalPages = 0, status, error } = useSelector(
    (state) => state.logs
  );
  const [params, setParams] = useState({
    page: 0,
    size: 12,
  });

  useEffect(() => {
    dispatch(fetchAuditLogs(params));
  }, [dispatch, params]);

  const handlePageChange = (page) => {
    setParams({ ...params, page });
  };

  return (
    <div className="w-full h-full flex flex-col">
      <Header />
      <div className="w-full h-screen flex">
        <Sidebar />
        <div className="flex flex-col h-screen w-full">
          <div className="p-6">
            <h1 className="text-2xl mb-6 font-semibold">Audit Logs</h1>
            {status === 'loading' && <div>Loading...</div>}
            {status === 'failed' && <div>Error: {error}</div>}
            <table className="min-w-full table-auto border-collapse border border-gray-200">
              <thead>
                <tr className="bg-gray-100 text-gray-600">
                  <th className="px-4 py-2 text-left">ID</th>
                  <th className="px-4 py-2 text-left">Ação</th>
                  <th className="px-4 py-2 text-left">Data</th>
                  <th className="px-4 py-2 text-left">Criado por</th>
                  <th className="px-4 py-2 text-left">Criação</th>
                </tr>
              </thead>
              <tbody>
                {content.length > 0 ? (
                  content.map((log) => (
                    <tr key={log.id} className="hover:bg-gray-50 dark:border-gray-700 cursor-pointer">
                      <td className="px-4 py-2 border-t">{log.id}</td>
                      <td className="px-4 py-2 border-t">{log.action}</td>
                      <td className="px-4 py-2 border-t">{log.data}</td>
                      <td className="px-4 py-2 border-t">{log.name}</td>
                      <td className="px-4 py-2 border-t">{log.createdAt}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="3" className="px-4 py-2 text-center">No logs found</td>
                  </tr>
                )}
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

export default AuditLogsPage;
