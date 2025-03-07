import { Outlet, NavLink } from "react-router-dom";
import "../styles/dashboard.css";

const DashboardLayout = () => {
  return (
    <div className="dashboard-container">
      {/* Sidebar */}
      <aside className="sidebar">
        <h2>Finance Tracker</h2>
        <div className="user-profile">
          <img src="/profile-placeholder.png" alt="User Profile" />
          <h3>Ronald D Targbeh</h3>
          <p>rdtargbeh@gmail.com</p>
        </div>
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

        {/* Bottom section */}
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
