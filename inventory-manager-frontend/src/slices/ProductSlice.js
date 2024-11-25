import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import Cookies from 'js-cookie';
import { toast } from 'react-toastify';

const initialState = {
  content: [],
  pageable: {
    pageNumber: 0,
    pageSize: 12,
    sort: {
      sorted: false,
      unsorted: true,
      empty: true
    },
    offset: 0,
    paged: true,
    unpaged: false
  },
  totalPages: 0,
  totalElements: 0,
  last: false,
  first: true,
  size: 12,
  number: 0,
  sort: {
    sorted: false,
    unsorted: true,
    empty: true
  },
  numberOfElements: 0,
  empty: true,
  status: 'idle',
  error: null,
  item: {},
};

const api = `${import.meta.env.VITE_BACKEND_URL}`;


export const getProducts = createAsyncThunk(
  'products/getAll',
  async (params, { rejectWithValue }) => {
    try {
      const response = await axios.get(
        `${api}/products?page=${params.page}&size=${params.size}`,
        {
          headers: {
            Authorization: `Bearer ${Cookies.get("token")}`,
          }
        }
      );
      console.log('response')

      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || 'Erro desconhecido');
    }
  }
);

export const getProductById = createAsyncThunk(
  'products/getProductById',
  async (id, { rejectWithValue }) => {
    try {
      const response = await axios.get(
        `${api}/products/${id}`,
        {
          headers: {
            Authorization: `Bearer ${Cookies.get("token")}`,
          }
        }
      );

      return response.data;
    } catch (error) {
      toast.error("Erro em atualizado produto")
      return rejectWithValue(error.response?.data?.message || 'Erro desconhecido');
    }
  }
);


export const updateProducts = createAsyncThunk(
  'products/updateProducts',
  async ({ id, data }, { rejectWithValue }) => {
    console.log(id)
    console.log(data)
    try {
      const response = await axios.patch(
        `${api}/products/${id}`, data,
        {
          headers: {
            Authorization: `Bearer ${Cookies.get("token")}`,
          }
        }
      );

      return response.data;
    } catch (error) {
      toast.error("Erro em atualizado produto")
      return rejectWithValue(error.response?.data?.message || 'Erro desconhecido');
    }
  }
);

export const createProduct = createAsyncThunk(
  'products/createProduct',
  async ({ data }, { rejectWithValue }) => {
    console.log(data)
    try {
      const response = await axios.post(
        `${api}/products`, data,
        {
          headers: {
            Authorization: `Bearer ${Cookies.get("token")}`,
          }
        }
      );

      return response.data;
    } catch (error) {
      toast.error("Erro em criar produto")
      return rejectWithValue(error.response?.data?.message || 'Erro desconhecido');
    }
  }
);

export const deleteProductById = createAsyncThunk(
  'products/deleteProduct',
  async ({ id }, { rejectWithValue }) => {
    try {
      const response = await axios.delete(
        `${api}/products/${id}`,
        {
          headers: {
            Authorization: `Bearer ${Cookies.get("token")}`,
          }
        }
      );

      return response.data;
    } catch (error) {
      toast.error("Erro em deletar produto")
      console.log(error)
      return rejectWithValue(error.response?.data?.message || 'Erro desconhecido');
    }
  }
);


const productSlice = createSlice({
  name: 'product',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(getProducts.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(getProducts.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.content = action.payload.content;
        state.totalPages = action.payload.totalPages;
        state.totalElements = action.payload.totalElements;
        state.error = null;
      })
      .addCase(getProducts.rejected, (state) => {
        state.status = 'failed';
        state.error = 'Erro desconhecido';
      })
      .addCase(getProductById.pending, (state) => {
        state.addProductStatus = 'loading';
      })
      .addCase(getProductById.fulfilled, (state, action) => {
        state.addProductStatus = 'succeeded';
        state.item = action.payload 
      })
      .addCase(getProductById.rejected, (state, action) => {
        state.addProductStatus = 'failed';
      })
      .addCase(updateProducts.pending, (state) => {
        state.addProductStatus = 'loading';
      })
      .addCase(updateProducts.fulfilled, (state, action) => {
        state.addProductStatus = 'succeeded';
        state.item = action.payload 
      })
      .addCase(updateProducts.rejected, (state, action) => {
        state.addProductStatus = 'failed';
      })
      .addCase(createProduct.pending, (state) => {
        state.addProductStatus = 'loading';
      })
      .addCase(createProduct.fulfilled, (state, action) => {
        state.addProductStatus = 'succeeded';
        state.item = action.payload 
      })
      .addCase(createProduct.rejected, (state, action) => {
        state.addProductStatus = 'failed';
      })
      .addCase(deleteProductById.pending, (state) => {
        state.addProductStatus = 'loading';
      })
      .addCase(deleteProductById.fulfilled, (state, action) => {
        state.addProductStatus = 'succeeded';
        state.item = action.payload 
      })
      .addCase(deleteProductById.rejected, (state, action) => {
        state.addProductStatus = 'failed';
      });
  },
});

export default productSlice.reducer;
