import axios from "axios";

// 지금 셋팅 따로 필요 없으니 일단 withCredentials만 on
export const api = axios.create({
  baseURL: "http://localhost:8080",
  timeout: 10000,
  withCredentials: true
})