// LOGIN PAGE

import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [userName, setUserName] = useState(""); // ✅ Fix username state
  const [password, setPassword] = useState("");
  const [error, setError] = useState(""); // ✅ Ensure error always has a default value
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log("🔍 Sending login request:", userName, password); // Log before API call

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        {
          userName, // ✅ Ensure username is correctly passed
          password,
        }
      );

      const { token, role } = response.data; // Extract token and role

      if (!token || !role) {
        throw new Error("Invalid response from server");
      }

      // Store token and role in localStorage
      localStorage.setItem("token", token);
      localStorage.setItem("role", role);

      console.log("✅ Token Saved:", token); // Debugging

      // ✅ Clear input fields after successful login
      setUserName("");
      setPassword("");
      setError(""); // Clear previous errors

      // ✅ Redirect based on role
      navigate("/dashboard");
      // navigate(
      //   role === "ADMIN" || role === "MANAGER"
      //     ? "/admin-dashboard"
      //     : "/dashboard"
      // );
    } catch (err) {
      console.error("❌ Login Error:", err.response?.data || err.message);
      // ✅ Set error only if there's an actual error
      setError("Invalid username or password");
    }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>

      <form onSubmit={handleSubmit}>
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
      {error && <p className="error">{error}</p>}
    </div>
  );
  s;
};

export default Login;
