import { useState } from "react";
import { Link } from "react-router-dom";
import Register from "../pages/register"; // ✅ Import Register Modal
import Login from "../pages/login"; // Import the Login component

const Navbar = () => {
  const [showLogin, setShowLogin] = useState(false);
  const [showRegister, setShowRegister] = useState(false);

  return (
    <>
      <nav className="navbar">
        <h2 className="logo">Personal Finance Tracker</h2>
        <ul className="nav-links">
          <li>
            <Link to="/" className="nav-link">
              Home
            </Link>
          </li>
          <li>
            <Link to="/about" className="nav-link">
              About
            </Link>
          </li>
          <button className="nav-btn" onClick={() => setShowLogin(true)}>
            Login
          </button>
          <button className="nav-btn" onClick={() => setShowRegister(true)}>
            Register
          </button>
        </ul>
      </nav>

      {/* ✅ Login Modal */}
      {showLogin && <Login closeModal={() => setShowLogin(false)} />}

      {/* ✅ Register Modal */}
      {showRegister && <Register closeModal={() => setShowRegister(false)} />}
    </>
  );
};

export default Navbar;
