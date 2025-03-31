// // LOGIN PAGE

import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../styles/register.css";

const Login = ({ closeModal }) => {
  const navigate = useNavigate(); // ✅ React Router for navigation
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
      // closeModal(); // ✅ Close the modal after login

      // ✅ Redirect based on role
      if (role === "ADMIN") {
        navigate("/admin-dashboard");
      } else {
        navigate("/dashboard");
      }
    } catch (err) {
      console.error("❌ Login Error:", err.response?.data || err.message);
      setError("Invalid username or password.");
    }
  };

  return (
    <div className="auth-container modal-overlay login-container">
      <div className="login-modal">
        <button
          type="button"
          className="login-close-btn"
          onClick={() => closeModal?.()}
          aria-label="Close Login Modal"
        >
          ×
        </button>

        <h2 className="login-header">Login</h2>
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
          <button type="submit" className="login-btn">
            Login
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
