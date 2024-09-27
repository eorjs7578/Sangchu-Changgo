import axios from "axios";

// 지금 셋팅 따로 필요 없으니 일단 withCredentials만 on
export const api = axios.create({
  baseURL: import.meta.env.VITE_VUE_API_URL,
  timeout: 10000,
  withCredentials: true
})