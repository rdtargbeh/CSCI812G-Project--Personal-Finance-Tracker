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
        <button className="close-btn-btn" onClick={closeModal}>
          X
        </button>
        <h2 className="header">Register</h2>
        {successMessage && <p className="success">{successMessage}</p>}
        {error && <p className="error">{error}</p>}
        <form onSubmit={handleRegister}>
          <table className="form-table">
            <tbody>
              <tr>
                <td>
                  <label>Username:</label>
                </td>
                <td>
                  <input
                    type="text"
                    name="userName"
                    value={user.userName}
                    onChange={handleChange}
                    required
                  />
                </td>
                <td>
                  <label>Email:</label>
                </td>
                <td>
                  <input
                    type="email"
                    name="email"
                    value={user.email}
                    onChange={handleChange}
                    required
                  />
                </td>
              </tr>

              <tr>
                <td>
                  <label>Password:</label>
                </td>
                <td>
                  <input
                    type="password"
                    name="password"
                    value={user.password}
                    onChange={handleChange}
                    required
                  />
                </td>
                <td>
                  <label>Phone Number:</label>
                </td>
                <td>
                  <input
                    type="text"
                    name="phoneNumber"
                    value={user.phoneNumber}
                    onChange={handleChange}
                  />
                </td>
              </tr>

              <tr>
                <td>
                  <label>First Name:</label>
                </td>
                <td>
                  <input
                    type="text"
                    name="firstName"
                    value={user.firstName}
                    onChange={handleChange}
                    required
                  />
                </td>
                <td>
                  <label>Currency:</label>
                </td>
                <td>
                  <select
                    name="currency"
                    value={user.currency}
                    onChange={handleChange}
                  >
                    <option value="USD">USD ($)</option>
                    <option value="EUR">EUR (€)</option>
                    <option value="GBP">GBP (£)</option>
                  </select>
                </td>
              </tr>
              <tr>
                <td>
                  <label>Last Name:</label>
                </td>
                <td>
                  <input
                    type="text"
                    name="lastName"
                    value={user.lastName}
                    onChange={handleChange}
                    required
                  />
                </td>
                <td>
                  <label>Timezone:</label>
                </td>
                <td>
                  <select
                    name="timezone"
                    value={user.timezone}
                    onChange={handleChange}
                  >
                    <option value="UTC">UTC</option>
                    <option value="PST">PST</option>
                    <option value="EST">EST</option>
                  </select>
                </td>
              </tr>

              <tr>
                <td>
                  <label>Address:</label>
                </td>
                <td colSpan="3">
                  <input
                    type="text"
                    name="address"
                    value={user.address}
                    onChange={handleChange}
                  />
                </td>
              </tr>

              <tr>
                <td>
                  <label>Pref Language:</label>
                </td>
                <td colSpan="3">
                  <select
                    name="preferredLanguage"
                    value={user.preferredLanguage}
                    onChange={handleChange}
                  >
                    <option value="en">English</option>
                    <option value="fr">French</option>
                    <option value="es">Spanish</option>
                  </select>
                </td>
              </tr>

              <tr>
                <td colSpan="4" className="form-buttons-row">
                  <div className="form-buttons">
                    <button type="submit" className="save-btn">
                      Register
                    </button>
                    <button
                      type="button"
                      onClick={closeModal}
                      className="cancel-btn"
                    >
                      Cancel
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </form>

        {successMessage && <p className="success-message">{successMessage}</p>}
        {error && <p className="error-message">{error}</p>}
      </div>
    </div>
  );
};

export default Register;
