import axios from "axios";

const { VITE_VUE_API_URL} = import.meta.env;

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
    withCredentials:true
  });
  instance.defaults.headers.common["Authorization"] = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTcyNzIzNjY1MywiZXhwIjoxNzI3MjQwMjUzfQ.QoTNj3VWdI_-AyELrdCN50PMw4G_DF3CGCsHFlexIbeEJOL3GnO5UEuZiHdBcbMBdyy16gDb2HOUAsrDBqEmiA";
  return instance;
}

export { localAxios };
