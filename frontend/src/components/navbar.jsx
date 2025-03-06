import { Link } from "react-router-dom";
import "./navbar.css"; // Import CSS file

const Navbar = () => {
  return (
    <nav className="navbar">
      <h2>Personal Finance Tracker</h2>
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
      </ul>
    </nav>
  );
};

export default Navbar;
