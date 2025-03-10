import { useState, useEffect } from "react";
import axiosInstance from "../service/axiosInstance";
import "../styles/accounts.css"; // ✅ Import CSS file

const Accounts = () => {
  const [accounts, setAccounts] = useState([]); // Store user accounts
  const [expandedAccount, setExpandedAccount] = useState(null); // Track expanded details
  const [userId, setUserId] = useState(null); // ✅ Store logged-in user ID

  // ✅ Fetch user profile to get userId
  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        const response = await axiosInstance.get("/users/profile");
        setUserId(response.data.userId); // ✅ Store userId
      } catch (error) {
        console.error(
          "❌ Error fetching user profile:",
          error.response?.data || error.message
        );
      }
    };
    fetchUserProfile();
  }, []);

  // ✅ Fetch accounts after userId is available
  useEffect(() => {
    if (!userId) return; // ✅ Wait for userId before fetching accounts

    const fetchAccounts = async () => {
      try {
        const response = await axiosInstance.get(`/accounts/user/${userId}`); // ✅ Fetch accounts with userId
        setAccounts(response.data); // ✅ Store fetched accounts
      } catch (error) {
        console.error(
          "❌ Error fetching accounts:",
          error.response?.data || error.message
        );
      }
    };
    fetchAccounts();
  }, [userId]); // ✅ Run when userId is set

  // ✅ Toggle Details View
  const toggleDetails = (accountId) => {
    setExpandedAccount((prev) => (prev === accountId ? null : accountId));
  };

  return (
    <div className="accounts-container">
      <h2>My Accounts</h2>

      {/* ✅ Display account cards */}
      <div className="account-list">
        {accounts.length > 0 ? (
          accounts.map((account) => (
            <div className="account-card" key={account.accountId}>
              <h3>{account.name}</h3>
              <p>
                Balance: {account.currency} {account.balance.toFixed(2)}
              </p>
              <button onClick={() => toggleDetails(account.accountId)}>
                {expandedAccount === account.accountId
                  ? "Hide Details"
                  : "View Details"}
              </button>

              {/* ✅ Show full details when expanded */}
              {expandedAccount === account.accountId && (
                <div className="account-details">
                  <table>
                    <tbody>
                      <tr>
                        <td>
                          <strong>Institution:</strong>
                        </td>
                        <td>{account.institutionName || "N/A"}</td>
                      </tr>
                      <tr>
                        <td>
                          <strong>Account Number:</strong>
                        </td>
                        <td>{account.accountNumber || "N/A"}</td>
                      </tr>
                      <tr>
                        <td>
                          <strong>Interest Rate:</strong>
                        </td>
                        <td>
                          {account.interestRate
                            ? `${account.interestRate}%`
                            : "N/A"}
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <strong>Created On:</strong>
                        </td>
                        <td>
                          {new Date(account.dateCreated).toLocaleDateString()}
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <strong>Last Updated:</strong>
                        </td>
                        <td>
                          {new Date(account.dateUpdated).toLocaleDateString()}
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          ))
        ) : (
          <p>No accounts found. Create one to get started!</p>
        )}
      </div>
    </div>
  );
};

export default Accounts;
