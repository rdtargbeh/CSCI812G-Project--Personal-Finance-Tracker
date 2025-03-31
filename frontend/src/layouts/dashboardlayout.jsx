import { Outlet, NavLink, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axiosInstance from "../service/axiosInstance";
import Profile from "../pages/profile"; // ✅ Import Profile component
import "../styles/dashboard.css";

const DashboardLayout = () => {
  const navigate = useNavigate();
  const [showProfile, setShowProfile] = useState(false); // ✅ Toggle Profile Visibility
  const [showEditProfile, setShowEditProfile] = useState(false); // ✅ Control profile modal
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

  // USER DELETE ACCOUNT FUNCTION
  const handleDeleteAccount = async () => {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete your account? This action cannot be undone."
    );

    if (!confirmDelete) return;

    try {
      const userId = user.userId; // ✅ Fetch logged-in user's ID
      await axiosInstance.delete(`/users/remove/${userId}`);

      alert("✅ Your account has been deleted.");
      localStorage.removeItem("token"); // ✅ Remove JWT token
      localStorage.removeItem("role");
      window.location.href = "/login"; // ✅ Redirect to login
    } catch (error) {
      console.error(
        "❌ Error deleting account:",
        error.response?.data || error.message
      );
      alert("❌ Failed to delete account. Please try again.");
    }
  };

  //  RETURN  +++++++++++++++++++++++
  return (
    <div className="dashboard-container">
      {/* Sidebar */}
      <aside className="sidebar">
        <h2>Finance Tracker</h2>

        {/* User Profile Section +++++++++++++++++++++++++++++++++++++*/}
        <div className="user-profile">
          {/* ✅ Profile Picture */}
          <img
            src={"/img/user-2.png"}
            alt="User Profile"
            className="profile-icon"
            onClick={() => setShowProfile(!showProfile)} // Toggle profile details
          />

          {/* ✅ Show Name/Username Below Profile Picture */}
          <h4 className="profile-name">
            {user?.firstName} {user?.lastName || user?.userName}
          </h4>

          {/* ✅ Profile Overlay (Now Stays Fixed) */}
          {showProfile && user && (
            <div className="profile-overlay">
              {/* Close Button (X) */}
              <button
                className="close-profile-btn"
                onClick={() => setShowProfile(false)}
              >
                ✖
              </button>
              <p className="profile-names">
                {user.firstName} {user.lastName}
              </p>
              <p className="profile-info">
                <span>📧</span> {user.email}
              </p>
              <p className="profile-info">
                <span>📞</span> {user.phoneNumber || "No phone number"}
              </p>
              <p className="profile-info">
                <span>🏠</span> {user.address || "No address"}
              </p>

              {/* Edit Profile */}
              <button
                className="edit-profile-btn"
                onClick={() => setShowEditProfile(true)}
              >
                Edit Profile
              </button>
              {/* Delete Account */}
              <button
                className="delete-account-btn"
                onClick={handleDeleteAccount}
              >
                Delete Account
              </button>
            </div>
          )}
        </div>

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

      {/* ✅ Edit Profile Modal (Properly Closeable) */}
      {showEditProfile && (
        <Profile closeModal={() => setShowEditProfile(false)} />
      )}
    </div>
  );
};

export default DashboardLayout;
