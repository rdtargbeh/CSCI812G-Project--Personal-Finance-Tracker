import { useEffect, useState } from "react";
import axiosInstance from "../service/axiosInstance";
import "../styles/transaction.css";

const STATUSES = ["PENDING", "COMPLETED", "FAILED"];
const INTERVALS = ["DAILY", "WEEKLY", "MONTHLY", "YEARLY"];

const TRANSACTION_TYPES = [
  "INCOME",
  "EXPENSE",
  "TRANSFER",
  "CREDIT_CARD_PAYMENT",
  "RECURRING",
];

const PAYMENT_METHODS = [
  "CASH",
  "BANK_TRANSFER",
  "DEBIT_CARD",
  "CREDIT_CARD",
  "DIRECT_DEPOSIT",
  "PAYPAL",
  "OTHER",
];

const TransactionForm = ({ isOpen, onClose, initialData }) => {
  const [form, setForm] = useState({
    transactionType: "INCOME",
    accountId: "",
    toAccountId: "",
    categoryId: "",
    amount: "",
    date: new Date().toISOString().slice(0, 16), // Default to now
    description: "",
    paymentMethod: "CASH",
    status: "PENDING",
    isRecurring: false,
    recurringInterval: "",
    nextDueDate: "",
  });

  const [accounts, setAccounts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [warning, setWarning] = useState(null); // ✅ New warning state

  // ✅ Fetch accounts/categories when modal opens
  useEffect(() => {
    if (!isOpen) return;

    const fetchData = async () => {
      try {
        const profile = await axiosInstance.get("/users/profile");
        const userId = profile.data.userId;

        const accRes = await axiosInstance.get(`/accounts/user/${userId}`);
        const catRes = await axiosInstance.get(`/categories/user/${userId}`);

        setAccounts(accRes.data);
        setCategories(catRes.data);
      } catch (err) {
        console.error("❌ Error fetching form data:", err);
      }
    };

    fetchData();
  }, [isOpen]);

  // ✅ Separate logic to prefill form from initialData
  useEffect(() => {
    if (!isOpen) return;

    if (initialData) {
      const categoryId =
        categories.find((c) => c.name === initialData.category)?.categoryId ||
        "";

      const accountId =
        accounts.find((a) => a.name === initialData.accountName)?.accountId ||
        "";

      const toAccountId =
        accounts.find((a) => a.name === initialData.accountName)?.accountId ||
        "";

      setForm({
        transactionType: initialData.transactionType || "INCOME",
        accountId,
        toAccountId,
        categoryId,
        amount: initialData.amount?.toString() || "",
        date: initialData.date
          ? new Date(initialData.date).toISOString().slice(0, 16)
          : new Date().toISOString().slice(0, 16),
        description: initialData.description || "",
        paymentMethod: initialData.paymentMethod || "CASH",
        status: initialData.status || "PENDING",
        isRecurring: initialData.isRecurring || false,
        recurringInterval: initialData.recurringInterval || "",
        nextDueDate: initialData.nextDueDate
          ? new Date(initialData.nextDueDate).toISOString().slice(0, 16)
          : "",
      });
    } else {
      // Default values for new transaction
      setForm({
        transactionType: "INCOME",
        accountId: "",
        toAccountId: "",
        categoryId: "",
        amount: "",
        date: new Date().toISOString().slice(0, 16),
        description: "",
        paymentMethod: "CASH",
        status: "PENDING",
        isRecurring: false,
        recurringInterval: "",
        nextDueDate: "",
      });
    }
  }, [initialData, isOpen]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;

    // If user selects recurringInterval, auto-set nextDueDate
    if (name === "recurringInterval") {
      const now = new Date();
      let next = new Date(now);
      switch (value) {
        case "DAILY":
          next.setDate(now.getDate() + 1);
          break;
        case "WEEKLY":
          next.setDate(now.getDate() + 7);
          break;
        case "MONTHLY":
          next.setMonth(now.getMonth() + 1);
          break;
        case "YEARLY":
          next.setFullYear(now.getFullYear() + 1);
          break;
        default:
          break;
      }

      setForm((prev) => ({
        ...prev,
        recurringInterval: value,
        nextDueDate: next.toISOString().slice(0, 16),
      }));
      return; // ✅ Exit here to skip the default setForm logic
    }

    // 🔁 Default update for other inputs
    setForm((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const profile = await axiosInstance.get("/users/profile");
      const userId = profile.data.userId;

      const payload = {
        ...form,
        amount: parseFloat(form.amount),
        userId,
      };

      // ✅ Remove recurring fields if not selected
      if (!form.isRecurring) {
        payload.recurringInterval = null;
        payload.nextDueDate = null;
      }

      const type = form.transactionType;
      let response;

      if (type === "CREDIT_CARD_PAYMENT") {
        await axiosInstance.post(
          `/transactions/withdraw/${userId}/${form.accountId}`,
          payload
        );
        await axiosInstance.post(
          `/transactions/withdraw/${userId}/${form.toAccountId}`,
          payload
        );
        alert("✅ Credit Card Payment processed.");
      } else if (type === "INCOME") {
        await axiosInstance.post(
          `/transactions/deposit/${userId}/${form.accountId}`,
          payload
        );
        alert("✅ Income recorded.");
      } else if (type === "EXPENSE") {
        await axiosInstance.post(
          `/transactions/withdraw/${userId}/${form.accountId}`,
          payload
        );
        alert("✅ Expense recorded.");
      } else if (type === "TRANSFER") {
        await axiosInstance.post(
          `/transactions/transfer/${userId}/${form.accountId}/${form.toAccountId}`,
          payload
        );
        alert("✅ Transfer successful.");
      } else if (type === "RECURRING") {
        await axiosInstance.post(`/transactions/recurring`, payload);
        alert("✅ Recurring transaction created.");
      }

      setWarning(null);
      onClose();
    } catch (err) {
      console.error("❌ Error submitting transaction:", err);
      if (err.response?.data) {
        setWarning(err.response.data);
      } else {
        alert("❌ Failed to process transaction.");
      }
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>{initialData ? "Edit Transaction" : "Add Transaction"}</h2>
        <form onSubmit={handleSubmit}>
          <table className="form-table">
            <tbody>
              <tr>
                <td>
                  <label>Type:</label>
                </td>
                <td>
                  <select
                    name="transactionType"
                    value={form.transactionType}
                    onChange={handleChange}
                  >
                    {TRANSACTION_TYPES.map((t) => (
                      <option key={t}>{t}</option>
                    ))}
                  </select>
                </td>

                <td>
                  <label>Amount:</label>
                </td>
                <td>
                  <input
                    type="number"
                    name="amount"
                    value={form.amount}
                    onChange={handleChange}
                    required
                  />
                </td>
              </tr>

              <tr>
                <td>
                  <label>Account:</label>
                </td>
                <td>
                  <select
                    name="accountId"
                    value={form.accountId}
                    onChange={handleChange}
                    required
                  >
                    <option value="">Select Account</option>
                    {accounts.map((acc) => (
                      <option key={acc.accountId} value={acc.accountId}>
                        {acc.name} ({acc.type})
                      </option>
                    ))}
                  </select>
                </td>

                {["TRANSFER", "CREDIT_CARD_PAYMENT"].includes(
                  form.transactionType
                ) && (
                  <>
                    <td>
                      <label>To Account:</label>
                    </td>
                    <td>
                      <select
                        name="toAccountId"
                        value={form.toAccountId}
                        onChange={handleChange}
                        required
                      >
                        <option value="">Select Destination</option>
                        {accounts.map((acc) => (
                          <option key={acc.accountId} value={acc.accountId}>
                            {acc.institutionName} ({acc.name})
                          </option>
                        ))}
                      </select>
                    </td>
                  </>
                )}
              </tr>

              <tr>
                <td>
                  <label>Category:</label>
                </td>
                <td>
                  <select
                    name="categoryId"
                    value={form.categoryId}
                    onChange={handleChange}
                    required
                  >
                    <option value="">Select Category</option>
                    {categories.map((cat) => (
                      <option key={cat.categoryId} value={cat.categoryId}>
                        {cat.name} ({cat.type})
                      </option>
                    ))}
                  </select>
                </td>

                <td>
                  <label>Date:</label>
                </td>
                <td>
                  <input
                    type="datetime-local"
                    name="date"
                    value={form.date}
                    onChange={handleChange}
                    required
                  />
                </td>
              </tr>

              <tr>
                <td>
                  <label>Payment:</label>
                </td>
                <td>
                  <select
                    name="paymentMethod"
                    value={form.paymentMethod}
                    onChange={handleChange}
                  >
                    {PAYMENT_METHODS.map((p) => (
                      <option key={p}>{p}</option>
                    ))}
                  </select>
                </td>

                <td>
                  <label>Status:</label>
                </td>
                <td>
                  <select
                    name="status"
                    value={form.status}
                    onChange={handleChange}
                  >
                    {STATUSES.map((s) => (
                      <option key={s}>{s}</option>
                    ))}
                  </select>
                </td>
              </tr>

              <tr>
                <td>
                  <label>Description:</label>
                </td>
                <td colSpan="3">
                  <input
                    name="description"
                    value={form.description}
                    onChange={handleChange}
                  />
                </td>
              </tr>

              <tr>
                <td>
                  <label>Recurring:</label>
                </td>
                <td>
                  <input
                    type="checkbox"
                    name="isRecurring"
                    checked={form.isRecurring}
                    onChange={handleChange}
                  />
                </td>

                {form.isRecurring && (
                  <>
                    <td>
                      <label>Interval:</label>
                    </td>
                    <td>
                      <select
                        name="recurringInterval"
                        value={form.recurringInterval}
                        onChange={handleChange}
                      >
                        <option value="">Select</option>
                        {INTERVALS.map((i) => (
                          <option key={i}>{i}</option>
                        ))}
                      </select>
                    </td>
                  </>
                )}
              </tr>

              {form.isRecurring && (
                <tr>
                  <td>
                    <label>Next Due Date:</label>
                  </td>
                  <td colSpan="3">
                    <input
                      type="datetime-local"
                      name="nextDueDate"
                      value={form.nextDueDate}
                      onChange={handleChange}
                      disabled
                    />
                  </td>
                </tr>
              )}

              <tr>
                <td colSpan="4" className="form-buttons-row">
                  <div className="form-buttons">
                    <button type="submit" className="save-btn">
                      Save
                    </button>
                    <button
                      type="button"
                      onClick={onClose}
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
      </div>
    </div>
  );
};

export default TransactionForm;
