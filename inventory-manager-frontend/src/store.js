import { configureStore } from '@reduxjs/toolkit';
import authReducer from './slices/AuthSlice';
import productReducer from './slices/ProductSlice'

export const store = configureStore({
  reducer: {
    auth: authReducer,
    product: productReducer
  },
});

export default store;
