import axios from "axios";

const api = axios.create({
  baseURL: process.env.MAIN_SERVER,
  timeout: 10000,
});

api.interceptors.request.use(
  config => {
    const accessToken = sessionStorage.getItem("accessToken");

    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  error => Promise.reject(error)
);

api.interceptors.response.use(
  response => {
    // 404
    if (response.status === 404) {
      // TODO: 에러 페이지가 있으면 에러 페이지로 푸시
    }
    return response;
  },
  error => {
    // 401
    if (error.response && error.response.status === 401) {
      
      // 리프레시 토큰
      const refreshToken = sessionStorage.getItem("refreshToken");
      if (refreshToken) {
        return axios.post('/refresh-token', { refreshToken })
          .then(res => {
            sessionStorage.setItem("accessToken", res.data.accessToken);
            error.config.headers.Authorization = `Bearer ${res.data.accessToken}`;
            return axios.request(error.config);
          })
          .catch(err => Promise.reject(err));
      }
    }
    return Promise.reject(error);
  }
);

export default api;
