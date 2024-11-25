import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import Cookies from 'js-cookie';

const api = `${import.meta.env.VITE_BACKEND_URL}/users`;

// Estado inicial
const initialState = {
  token: null,
  uuid: null,
  name: null,
  status: 'idle',
  error: null,
};

export const login = createAsyncThunk(
  'auth/login',
  async (credentials, { rejectWithValue }) => {
    try {
      const formData = new FormData();
      formData.append('username', credentials.email);
      formData.append('password', credentials.password);

      const response = await axios.post(
        `${api}/login`,
        { email: credentials.email, password: credentials.password },
      );

      Cookies.set('token', response.data.token);

      return response;
    } catch (error) {
      console.log(error);
      return rejectWithValue(error.response?.data?.message || 'Erro desconhecido');
    }
  }
);

export const registerUser = createAsyncThunk('auth/registerUser', async (userData, { rejectWithValue }) => {
  try {
    const response = await axios.post(`${api}`, userData);
    return response.data; 
  } catch (error) {
    return rejectWithValue(error.response.data); 
  }
});

export const logout = createAsyncThunk('auth/logout', async () => {
  Cookies.remove('token');
});

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(login.pending, (state) => {
        state.status = 'loading';
        state.error = null; 
      })
      .addCase(login.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.token = action.payload.token;
        state.error = null;
      })
      .addCase(login.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload || 'Erro desconhecido'; 
      })
      .addCase(logout.fulfilled, (state) => {
        state.token = null;
        state.uuid = null;
        state.name = null;
        state.status = 'idle';
        state.error = null;
      })
      .addCase(registerUser.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(registerUser.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.user = action.payload;
      })
      .addCase(registerUser.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export default authSlice.reducer;
