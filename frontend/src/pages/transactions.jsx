import { useState } from "react";
import TransactionForm from "./transactionForm.jsx";
import Category from "../pages/category.jsx"; // optional, for modal
import "../styles/transaction.css";

const Transaction = () => {
  const [showForm, setShowForm] = useState(false);
  const [showCategoryModal, setShowCategoryModal] = useState(false);

  return (
    <div className="transaction-page">
      <h2>💼 My Transactions</h2>

      {/* 🔍 Filters */}
      <div className="transaction-filters">
        <input type="text" placeholder="Search transactions..." />
        <input type="date" />
        <input type="date" />
      </div>

      {/* 💰 Summary + Action Buttons */}
      <div className="transaction-summary">
        <div className="summary-card income">
          <h4>Total Income</h4>
          <p>$0.00</p> {/* Replace with dynamic value later */}
        </div>
        <div className="summary-card expense">
          <h4>Total Expense</h4>
          <p>$0.00</p>
        </div>
        <div className="summary-card action" onClick={() => setShowForm(true)}>
          <h4>➕ AddNew Transaction</h4>
        </div>
        <div
          className="summary-card action"
          onClick={() => setShowCategoryModal(true)}
        >
          <h4>📂 Manage Categories</h4>
        </div>
      </div>

      {/* 🔟 Recent Transactions Placeholder */}
      <div className="transaction-list">
        <h3>🕓 Recent Transactions</h3>
        <p>(List will show up to 10 recent items)</p>
      </div>

      {/* ➕ Transaction Modal */}
      <TransactionForm
        isOpen={showForm}
        onClose={() => setShowForm(false)}
        initialData={null}
      />

      {/* 📂 Category Modal */}
      {showCategoryModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <button
              className="close-btn"
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
