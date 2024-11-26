import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import Cookies from 'js-cookie';

const api = `${import.meta.env.VITE_BACKEND_URL}/logs`;

const initialState = {
  content: [],
  status: 'idle',
  error: null,
  totalPages: 0,
  totalElements: 0,
  pageNumber: 0,
  pageSize: 12,
};

export const fetchAuditLogs = createAsyncThunk(
  'auditLogs/fetchAuditLogs',
  async ({ page, size }, { rejectWithValue }) => {
    try {
      const response = await axios.get(api, {
        params: {
          page: page,
          size: size,
        },
        headers: {
          Authorization: `Bearer ${Cookies.get("token")}`,
        }
      });
      return response.data;
    } catch (error) {
      console.log(error);
      return rejectWithValue(error.response?.data?.message || 'Erro desconhecido');
    }
  }
);

const auditLogsSlice = createSlice({
  name: 'auditLogs',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchAuditLogs.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchAuditLogs.fulfilled, (state, action) => {
        state.status = 'succeeded';
        console.log(action.payload.content)
        state.content = action.payload.content;
        state.totalPages = action.payload.totalPages;
        state.totalElements = action.payload.totalElements;
        state.pageNumber = action.payload.number;
        state.pageSize = action.payload.size;
        state.error = null;
      })
      .addCase(fetchAuditLogs.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload || 'Erro desconhecido';
      });
  },
});

export default auditLogsSlice.reducer;
