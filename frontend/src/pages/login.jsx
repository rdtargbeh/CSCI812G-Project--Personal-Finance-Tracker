// LOGIN PAGE

import { useState } from "react";
import axios from "axios";

const Login = ({ closeModal }) => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  // ✅ Handle Login Submission
  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        { userName, password }
      );

      const { token, role } = response.data;
      localStorage.setItem("token", token);
      localStorage.setItem("role", role);

      alert("✅ Login successful!");
      closeModal(); // ✅ Close the modal after login
      window.location.reload(); // ✅ Refresh to update authentication state
    } catch (err) {
      console.error("❌ Login Error:", err.response?.data || err.message);
      setError("Invalid username or password.");
    }
  };

  return (
    <div className="auth-container modal-overlay">
      <div className="modal-content">
        <button className="close-btn" onClick={closeModal}>
          X
        </button>
        <h2>Login</h2>
        {error && <p className="error">{error}</p>}

        <form onSubmit={handleLogin}>
          <input
            type="text"
            placeholder="Username"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
};

export default Login;

// import { useState } from "react";
// import axios from "axios";
// import { useNavigate } from "react-router-dom"; // ✅ Import navigate

// const Login = () => {
//   const [userName, setUserName] = useState("");
//   const [password, setPassword] = useState("");
//   const [error, setError] = useState("");
//   const navigate = useNavigate(); // ✅ Define navigate

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     setError("");

//     try {
//       const response = await axios.post(
//         "http://localhost:8080/api/auth/login",
//         {
//           userName,
//           password,
//         }
//       );

//       const { token, role } = response.data;
//       if (!token || !role) throw new Error("Invalid response from server");

//       localStorage.setItem("token", token);
//       localStorage.setItem("role", role);

//       console.log("✅ Token Saved:", token);

//       setUserName("");
//       setPassword("");
//       setError("");

//       navigate(
//         role === "ADMIN" || role === "MANAGER"
//           ? "/admin-dashboard"
//           : "/dashboard"
//       );
//     } catch (err) {
//       console.error("❌ Login Error:", err.response?.data || err.message);
//       setError("Invalid username or password");
//     }
//   };

//   return (
//     <div className="login-container">
//       <h2>Login</h2>

//       <form onSubmit={handleSubmit}>
//         <input
//           type="text"
//           placeholder="Username"
//           value={userName}
//           onChange={(e) => setUserName(e.target.value)}
//           required
//         />
//         <input
//           type="password"
//           placeholder="Password"
//           value={password}
//           onChange={(e) => setPassword(e.target.value)}
//           required
//         />
//         <button type="submit">Login</button>
//       </form>
//       {error && <p className="error">{error}</p>}
//     </div>
//   );
// };

// export default Login;
