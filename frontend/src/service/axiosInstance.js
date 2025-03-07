import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "http://localhost:8080/api",
});

// ✅ Attach JWT token to every request
axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem("token"); // 🔹 Get token from local storage
  if (token) {
    config.headers.Authorization = `Bearer ${token}`; // 🔹 Attach token
  }
  return config;
});

export default axiosInstance;
