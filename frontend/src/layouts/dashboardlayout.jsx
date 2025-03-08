import { Outlet, NavLink, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axiosInstance from "../service/axiosInstance";
import "../styles/dashboard.css";
// import "../styles/global.css";

const DashboardLayout = () => {
  const navigate = useNavigate();
  const [showProfile, setShowProfile] = useState(false); // ✅ Toggle Profile Visibility
  const [user, setUser] = useState(null); // ✅ Store user data

  // ✅ Fetch User Profile Data
  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        console.log("Fetching user profile...");
        const response = await axiosInstance.get("/users/profile"); // ✅ API call with JWT
        console.log("User Data Received:", response.data);
        setUser(response.data); // ✅ Store user data in state
      } catch (error) {
        console.error(
          "❌ Error fetching user profile:",
          error.response?.data || error.message
        );
      }
    };
    fetchUserProfile();
  }, []);

  // ✅ Logout function
  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    navigate("/login");
  };

  return (
    <div className="dashboard-container">
      {/* Sidebar */}
      <aside className="sidebar">
        <h2>Finance Tracker</h2>

        {/* User Profile Section */}
        <div className="user-profile">
          {/* ✅ Profile Picture */}
          <img
            src={user?.profilePicture || "/img/user-1.png"}
            alt="User Profile"
            className="profile-icon"
            onClick={() => setShowProfile(!showProfile)} // Toggle profile details
          />

          {/* ✅ Show Name/Username Below Profile Picture */}
          <h4 className="profile-name">
            {user?.firstName} {user?.lastName || user?.userName}
          </h4>
        </div>

        {/* ✅ Profile Details (Below Profile Picture) */}
        {showProfile && user && (
          <div className="profile-overlay">
            <p className="profile-names">
              {user.firstName} {user.lastName}
            </p>
            <p className="profile-info info">
              <span>📧</span> {user.email}
            </p>
            <p className="profile-info">
              <span>📞</span> {user.phoneNumber || "No phone number"}
            </p>
            <p className="profile-info info">
              <span>🏠</span> {user.address || "No address"}
            </p>
            <NavLink to="/dashboard/profile">
              <button className="edit-profile-btn">Edit Profile</button>
            </NavLink>
          </div>
        )}

        {/* Sidebar Navigation */}
        <nav>
          <ul>
            <li>
              <NavLink to="/dashboard">Dashboard</NavLink>
            </li>
            <li>
              <NavLink to="/dashboard/accounts">Accounts</NavLink>
            </li>
            <li>
              <NavLink to="/dashboard/transactions">Transactions</NavLink>
            </li>
            <li>
              <NavLink to="/dashboard/loans">Loans</NavLink>
            </li>
            <li>
              <NavLink to="/dashboard/savings">Savings Goals</NavLink>
            </li>
            <li>
              <NavLink to="/dashboard/investments">Investments</NavLink>
            </li>
            <li>
              <NavLink to="/dashboard/reports">Reports</NavLink>
            </li>
            <li>
              <NavLink to="/dashboard/subscriptions">Subscriptions</NavLink>
            </li>
          </ul>
        </nav>

        {/* ✅ Logout Button at Bottom */}
        <button className="logout-btn" onClick={handleLogout}>
          Logout
        </button>
      </aside>

      {/* Main Content */}
      <main className="main-content">
        <header className="top-bar">
          <div className="stats">
            <div className="stat-items">
              <h4>Account Balance</h4>
              <p>$12,345.67</p>
            </div>
            <div className="stat-items">
              <h4>Investments</h4>
              <p>$8,000 / 10,000</p>
            </div>
            <div className="stat-items">
              <h4>Loans</h4>
              <p>$5,000 / 15,000</p>
            </div>
          </div>
        </header>

        {/* Dynamic Content */}
        <section className="content">
          <Outlet />
        </section>

        {/* Bottom Section */}
        <footer className="bottom-section">
          <div className="recent-transactions">
            <h3>Recent Transactions</h3>
            <ul>
              <li>💰 Salary Received - $3,000</li>
              <li>🛒 Grocery Shopping - $120</li>
              <li>🏠 Loan Payment - $500</li>
            </ul>
          </div>
          <div className="quick-reports">
            <h3>Quick Reports</h3>
            <p>📊 Monthly Expenses: $2,500</p>
            <p>📈 Investment Growth: 8%</p>
          </div>
        </footer>
      </main>
    </div>
  );
};

export default DashboardLayout;
