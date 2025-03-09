// USER REGISTER OR CREATE ACCOUNT PAGE

import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../styles/register.css"; // ✅ Import styles

const Register = ({ closeModal }) => {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    username: "",
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    phoneNumber: "",
    address: "",
    currency: "USD", // ✅ Default value
    timezone: "UTC", // ✅ Default value
    preferredLanguage: "en", // ✅ Default value
  });

  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  // ✅ Handle Input Change
  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  // ✅ Handle Form Submission
  const handleRegister = async (e) => {
    e.preventDefault();
    setError("");
    setSuccessMessage("");

    try {
      console.log("🔹 Sending registration request:", user);
      const response = await axios.post(
        "http://localhost:8080/api/auth/register",
        user
      );

      console.log("✅ Registration Successful:", response.data);
      setSuccessMessage(
        "✅ Registration successful! Please check your email for verification."
      );
      setTimeout(closeModal, 2000); // ✅ Close modal after 2 seconds

      // ✅ Clear form fields
      setUser({
        userName: "",
        email: "",
        password: "",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        address: "",
        currency: "USD",
        timezone: "UTC",
        preferredLanguage: "en",
      });

      // ✅ Redirect to login page after registration
      setTimeout(() => navigate("/login"), 3000);
    } catch (err) {
      console.error(
        "❌ Registration Error:",
        err.response?.data || err.message
      );
      setError(err.response?.data?.message || "Registration failed.");
    }
  };

  return (
    <div className="auth-container modal-overlay">
      <div className="modal-content">
        <button className="close-btn" onClick={closeModal}>
          X
        </button>
        <h2>Register</h2>
        {successMessage && <p className="success">{successMessage}</p>}
        {error && <p className="error">{error}</p>}

        <form onSubmit={handleRegister}>
          <input
            type="text"
            name="userName"
            placeholder="Username"
            value={user.userName}
            onChange={handleChange}
            required
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={user.email}
            onChange={handleChange}
            required
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={user.password}
            onChange={handleChange}
            required
          />
          <input
            type="text"
            name="firstName"
            placeholder="First Name"
            value={user.firstName}
            onChange={handleChange}
            required
          />
          <input
            type="text"
            name="lastName"
            placeholder="Last Name"
            value={user.lastName}
            onChange={handleChange}
            required
          />
          <input
            type="text"
            name="phoneNumber"
            placeholder="Phone Number"
            value={user.phoneNumber}
            onChange={handleChange}
          />
          <input
            type="text"
            name="address"
            placeholder="Address"
            value={user.address}
            onChange={handleChange}
          />

          {/* ✅ Select Dropdowns */}
          <select name="currency" value={user.currency} onChange={handleChange}>
            <option value="USD">USD ($)</option>
            <option value="EUR">EUR (€)</option>
            <option value="GBP">GBP (£)</option>
          </select>

          <select name="timezone" value={user.timezone} onChange={handleChange}>
            <option value="UTC">UTC</option>
            <option value="PST">PST</option>
            <option value="EST">EST</option>
          </select>

          <select
            name="preferredLanguage"
            value={user.preferredLanguage}
            onChange={handleChange}
          >
            <option value="en">English</option>
            <option value="fr">French</option>
            <option value="es">Spanish</option>
          </select>

          <button type="submit">Register</button>
        </form>
        {successMessage && <p className="success-message">{successMessage}</p>}
        {error && <p className="error-message">{error}</p>}
      </div>
    </div>
  );
};

export default Register;
