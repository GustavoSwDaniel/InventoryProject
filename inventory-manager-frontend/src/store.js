import { configureStore } from '@reduxjs/toolkit';
import authReducer from './slices/AuthSlice';
import productReducer from './slices/ProductSlice'
import auditLogsSlice from './slices/AuditLogsSlice'


export const store = configureStore({
  reducer: {
    auth: authReducer,
    product: productReducer,
    logs: auditLogsSlice
  },
});

export default store;
