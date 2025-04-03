import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "http://localhost:8080/api",
});

// ✅ Attach JWT token to every request
axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
    console.log("✅ Axios is sending token:", token);
  } else {
    console.warn("⚠️ No token found in localStorage.");
  }

  return config;
});

export default axiosInstance;

// import axios from "axios";

// const axiosInstance = axios.create({
//   baseURL: "http://localhost:8080/api",
// });

// // ✅ Attach JWT token to every request
// axiosInstance.interceptors.request.use((config) => {
//   const token = localStorage.getItem("token"); // 🔹 Get token from local storage
//   if (token) {
//     config.headers.Authorization = `Bearer ${token}`; // 🔹 Attach token
//   }
//   return config;
// });

// export default axiosInstance;
