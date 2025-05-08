import { useEffect, useState } from "react";
import axiosInstance from "../service/axiosInstance";
import "../styles/budget.css";

const Budget = () => {
  const [budgets, setBudgets] = useState([]);
  const [filteredBudgets, setFilteredBudgets] = useState([]);
  const [report, setReport] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [editBudget, setEditBudget] = useState(null);
  const [formData, setFormData] = useState({
    description: "",
    amountLimit: "",
    startDate: "",
    endDate: "",
    budgetType: "FLEXIBLE",
    rolloverAmount: "",
    categoryId: "",
  });
  const [categories, setCategories] = useState([]);
  const [showConfirmRestore, setShowConfirmRestore] = useState(false);
  const [restoreId, setRestoreId] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchBudgets();
    fetchCategories();
  }, []);

  const fetchBudgets = async () => {
    try {
      const profile = await axiosInstance.get("/users/profile");
      const userId = profile.data.userId;
      const res = await axiosInstance.get(`/budgets/report?userId=${userId}`);
      setBudgets(res.data.budgets);
      setReport(res.data);
      setFilteredBudgets(res.data.budgets);
    } catch (err) {
      console.error("Error fetching budgets:", err);
    }
  };

  const fetchCategories = async () => {
    try {
      const profile = await axiosInstance.get("/users/profile");
      const res = await axiosInstance.get(
        `/categories/user/${profile.data.userId}`
      );
      setCategories(res.data);
    } catch (err) {
      console.error("Error fetching categories:", err);
    }
  };

  const applyFilters = () => {
    let result = budgets;
    if (searchQuery) {
      const q = searchQuery.toLowerCase();
      result = result.filter(
        (b) =>
          b.description?.toLowerCase().includes(q) ||
          b.category?.toLowerCase().includes(q) ||
          b.budgetType?.toLowerCase().includes(q)
      );
    }
    if (startDate) {
      result = result.filter(
        (b) => new Date(b.startDate) >= new Date(startDate)
      );
    }
    if (endDate) {
      result = result.filter((b) => new Date(b.endDate) <= new Date(endDate));
    }
    setFilteredBudgets(result);
  };

  const handleReset = () => {
    setSearchQuery("");
    setStartDate("");
    setEndDate("");
    setFilteredBudgets(budgets);
  };

  const openForm = (budget = null) => {
    setError("");
    setEditBudget(budget);
    setFormData(
      budget
        ? {
            ...budget,
            budgetId: budget.budgetId,
            categoryId:
              categories.find((c) => c.name === budget.category)?.categoryId ||
              "",
          }
        : {
            description: "",
            amountLimit: "",
            startDate: "",
            endDate: "",
            budgetType: "FLEXIBLE",
            rolloverAmount: "",
            categoryId: "",
          }
    );
    setShowForm(true);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    const overlap = budgets.some((b) => {
      return (
        b.categoryId === formData.categoryId &&
        b.budgetId !== formData.budgetId &&
        !(
          new Date(formData.endDate) < new Date(b.startDate) ||
          new Date(formData.startDate) > new Date(b.endDate)
        )
      );
    });

    if (overlap) {
      return setError(
        "⚠️ A budget already exists for this category and date range."
      );
    }

    try {
      if (formData.budgetId) {
        await axiosInstance.put(`/budgets/${formData.budgetId}`, formData);
      } else {
        const profile = await axiosInstance.get("/users/profile");
        const userId = profile.data.userId;
        await axiosInstance.post("/budgets", { ...formData, userId });
      }

      setShowForm(false);
      setEditBudget(null);
      fetchBudgets();
    } catch (err) {
      console.error("Error saving budget:", err);
    }
  };

  const handleDelete = async (id) => {
    if (!id) {
      console.warn("⛔ Tried to delete a budget with no ID.");
      return;
    }

    try {
      await axiosInstance.delete(`/budgets/${id}`);
      fetchBudgets();
    } catch (err) {
      console.error("❌ Error deleting budget:", err);
      alert("Failed to delete. Please try again.");
    }
  };

  const handleConfirmRestore = (id) => {
    setRestoreId(id);
    setShowConfirmRestore(true);
  };

  const confirmRestore = async () => {
    setShowConfirmRestore(false);
    setRestoreId(null);
    fetchBudgets();
  };

  return (
    <div className="budget-page">
      <h2 className="budget-head">📊 My Budgets</h2>

      <div className="budget-header">
        <input
          type="text"
          placeholder="Search by description, category, type"
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
        <button onClick={applyFilters}>Apply Filters</button>
        <button onClick={handleReset}>Reset</button>
      </div>

      <div className="totals-wrapper">
        <div className="totals-item">
          <span>Total Limit: ${report?.totalBudgetLimit || 0}</span>
        </div>
        <div className="totals-item">
          <span>Rollover: ${report?.totalRolloverAmount || 0}</span>
        </div>
      </div>

      <div className="budget-controls">
        <button onClick={() => openForm(null)}>➕ Add New Budget</button>
      </div>

      <table className="budget-table">
        <thead>
          <tr>
            <th>Description</th>
            <th>Spent / Limit</th>
            {/* <th>Spent</th> */}
            <th>Start</th>
            <th>End</th>
            <th>Type</th>
            <th>Rollover</th>
            <th>Created</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody className="budget-tbody">
          {filteredBudgets.map((b) => (
            <tr key={b.budgetId} className={b.isDeleted ? "deleted-row" : ""}>
              <td>{b.description}</td>
              {/* <td>${b.amountLimit}</td> */}
              <td>
                <strong>
                  ${b.spent} / ${b.amountLimit}
                </strong>
                <div className="budget-bar">
                  <div
                    className="budget-progress"
                    style={{
                      width: `${Math.min(b.percentageUsed, 100)}%`,
                      backgroundColor:
                        b.percentageUsed > 100
                          ? "red"
                          : b.percentageUsed >= 80
                          ? "orange"
                          : "green",
                    }}
                  />
                </div>
              </td>
              <td>{b.startDate}</td>
              <td>{b.endDate}</td>
              <td>{b.budgetType}</td>
              <td>${b.rolloverAmount}</td>
              <td>{new Date(b.dateCreated).toLocaleDateString()}</td>
              <td className="actions">
                {!b.isDeleted ? (
                  <>
                    <button className="edit-btns" onClick={() => openForm(b)}>
                      ✏️ Edit
                    </button>
                    <button
                      className="delete-btns"
                      onClick={() => {
                        console.log("Deleting budget with ID:", b.budgetId); // Debug line
                        handleDelete(b.budgetId);
                      }}
                    >
                      🗑 Delete
                    </button>
                  </>
                ) : (
                  <button onClick={() => handleConfirmRestore(b.budgetId)}>
                    ♻️ Restore
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Modal Form */}
      {showForm && (
        <div className="budget-modal-overlay">
          <div className="budget-modal">
            <button className="close-btn" onClick={() => setShowForm(false)}>
              ×
            </button>
            <h3>{editBudget ? "Edit Budget" : "Add Budget"}</h3>
            {error && <p className="error-msg">{error}</p>}
            <form onSubmit={handleSubmit}>
              <input
                name="description"
                placeholder="Description"
                value={formData.description}
                onChange={handleChange}
                required
              />
              <input
                type="number"
                name="amountLimit"
                placeholder="Amount Limit"
                value={formData.amountLimit}
                onChange={handleChange}
                required
              />
              <input
                type="date"
                name="startDate"
                value={formData.startDate}
                onChange={handleChange}
                required
              />
              <input
                type="date"
                name="endDate"
                value={formData.endDate}
                onChange={handleChange}
                required
              />
              <select
                name="budgetType"
                value={formData.budgetType}
                onChange={handleChange}
              >
                <option value="FLEXIBLE">FLEXIBLE</option>
                <option value="STRICT">STRICT</option>
              </select>
              <input
                type="number"
                name="rolloverAmount"
                placeholder="Rollover Amount"
                value={formData.rolloverAmount}
                onChange={handleChange}
              />
              <select
                name="categoryId"
                value={formData.categoryId}
                onChange={handleChange}
                required
              >
                <option value="">Select Category</option>
                {categories.map((cat) => (
                  <option key={cat.categoryId} value={cat.categoryId}>
                    {cat.name}
                  </option>
                ))}
              </select>
              <div className="form-buttons">
                <button type="submit">💾 Save</button>
                <button type="button" onClick={() => setShowForm(false)}>
                  ❌ Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Restore Confirmation Modal */}
      {showConfirmRestore && (
        <div className="modal-overlay">
          <div className="modal">
            <p>Are you sure you want to restore this budget?</p>
            <button onClick={confirmRestore}>Yes, Restore</button>
            <button onClick={() => setShowConfirmRestore(false)}>Cancel</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Budget;
