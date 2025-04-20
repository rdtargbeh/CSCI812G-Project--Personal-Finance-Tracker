import { useEffect, useState } from "react";
import TransactionForm from "./transactionForm.jsx";
import { useNavigate } from "react-router-dom";
import Category from "../pages/category.jsx";
import axiosInstance from "../service/axiosInstance";
import "../styles/transaction.css";

const Transaction = () => {
  const [showForm, setShowForm] = useState(false);
  const [selectedTransaction, setSelectedTransaction] = useState(null);
  const [showCategoryModal, setShowCategoryModal] = useState(false);
  const [allTransactions, setAllTransactions] = useState([]);
  const [accounts, setAccounts] = useState([]);

  const [searchQuery, setSearchQuery] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [selectedAccount, setSelectedAccount] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        const profile = await axiosInstance.get("/users/profile");
        const userId = profile.data.userId;

        const txRes = await axiosInstance.get(`/transactions/user/${userId}`);
        setAllTransactions(txRes.data);

        const accRes = await axiosInstance.get(`/accounts/user/${userId}`);
        setAccounts(accRes.data);
      } catch (err) {
        console.error("❌ Error fetching data:", err);
      }
    };

    fetchInitialData();
  }, []);

  useEffect(() => {
    const fetchByAccount = async () => {
      if (!selectedAccount) return;

      try {
        const res = await axiosInstance.get(
          `/transactions/account/${selectedAccount}`
        );
        setAllTransactions(res.data);
      } catch (err) {
        console.error("❌ Error fetching transactions by account:", err);
      }
    };

    fetchByAccount();
  }, [selectedAccount]);

  const handleResetFilters = async () => {
    setSearchQuery("");
    setStartDate("");
    setEndDate("");
    setSelectedAccount("");

    try {
      const profile = await axiosInstance.get("/users/profile");
      const userId = profile.data.userId;
      const res = await axiosInstance.get(`/transactions/user/${userId}`);
      setAllTransactions(res.data);
    } catch (err) {
      console.error("❌ Error resetting transactions:", err);
    }
  };

  const handleEditTransaction = (transaction) => {
    setSelectedTransaction(transaction);
    setShowForm(true);
  };

  const filteredTransactions = allTransactions.filter((tx) => {
    const q = searchQuery.toLowerCase();
    const txDate = new Date(tx.date);

    const matchSearch =
      tx.description?.toLowerCase().includes(q) ||
      tx.category?.toLowerCase().includes(q) ||
      tx.transactionType?.toLowerCase().includes(q) ||
      tx.paymentMethod?.toLowerCase().includes(q) ||
      String(tx.amount).includes(q) ||
      tx.status?.toLowerCase().includes(q);

    const matchDate =
      (!startDate || txDate >= new Date(startDate)) &&
      (!endDate || txDate <= new Date(endDate));

    return matchSearch && matchDate;
  });

  const totalIncome = filteredTransactions
    .filter((tx) => tx.transactionType === "INCOME")
    .reduce((sum, tx) => sum + tx.amount, 0);

  const totalExpense = filteredTransactions
    .filter(
      (tx) =>
        tx.transactionType === "EXPENSE" ||
        tx.transactionType === "CREDIT_CARD_PAYMENT" ||
        tx.transactionType === "RECURRING"
    )
    .reduce((sum, tx) => sum + tx.amount, 0);

  return (
    <div className="transaction-page">
      <h2 className="transaction-page-header">💼 My Transactions</h2>

      {/* 🔍 Filters */}
      <div className="transaction-filters">
        <input
          type="text"
          placeholder="Search..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
        <input
          type="date"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
        />
        <input
          type="date"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
        />
        <select
          value={selectedAccount}
          onChange={(e) => setSelectedAccount(e.target.value)}
        >
          <option value="">All Accounts</option>
          {accounts.map((acc) => (
            <option key={acc.accountId} value={acc.accountId}>
              {acc.name}
            </option>
          ))}
        </select>
        <button onClick={handleResetFilters}>Reset</button>
      </div>

      {/* 💰 Summary + Action Buttons */}
      <div className="transaction-summary">
        <div className="summary-card income">
          <h4>Total Income</h4>
          <p>${totalIncome.toFixed(2)}</p>
        </div>
        <div className="summary-card expense">
          <h4>Total Expense</h4>
          <p>${totalExpense.toFixed(2)}</p>
        </div>
        <div className="alert overspending-alert">
          ⚠️ Warning: You're spending more than you earn!
        </div>

        <div
          className="summary-card action"
          onClick={() => {
            setSelectedTransaction(null);
            setShowForm(true);
          }}
        >
          <h4>➕ Add Transaction</h4>
        </div>
        <div
          className="summary-card action"
          onClick={() => setShowCategoryModal(true)}
        >
          <h4>📂 Manage Categories</h4>
        </div>
      </div>

      {/* 🕓 All Transactions Table */}
      <div className="transaction-header">
        <h3>🕓 All Transactions</h3>
      </div>

      <table className="recent-table">
        <thead>
          <tr>
            <th>Date</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Account</th>
            <th>Category</th>
            <th>Description</th>
            <th>Payment Method</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody className="table-body">
          {filteredTransactions.length === 0 ? (
            <tr>
              <td colSpan="9">No transactions found.</td>
            </tr>
          ) : (
            filteredTransactions.map((tx) => (
              <tr key={tx.transactionId}>
                <td>{new Date(tx.date).toLocaleDateString()}</td>
                <td>{tx.transactionType}</td>
                <td>${tx.amount}</td>
                <td>{tx.accountName || "N/A"}</td>
                <td>{tx.category}</td>
                <td>{tx.description}</td>
                <td>{tx.paymentMethod}</td>
                <td>{tx.status}</td>
                <td>
                  <button
                    className="edit-btn"
                    onClick={() => handleEditTransaction(tx)}
                  >
                    ✏️ Edit
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>

      {/* ➕ Transaction Modal */}
      <TransactionForm
        isOpen={showForm}
        onClose={() => setShowForm(false)}
        initialData={selectedTransaction}
      />

      {/* 📂 Category Modal */}
      {showCategoryModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <button
              className="close-btns"
              onClick={() => setShowCategoryModal(false)}
            >
              ×
            </button>
            <Category />
          </div>
        </div>
      )}
    </div>
  );
};

export default Transaction;
