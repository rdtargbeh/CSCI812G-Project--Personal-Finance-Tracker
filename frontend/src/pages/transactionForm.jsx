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
    date: "",
    description: "",
    paymentMethod: "CASH",
    status: "PENDING",
    isRecurring: false,
    recurringInterval: "",
    nextDueDate: "",
  });

  const [accounts, setAccounts] = useState([]);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    if (isOpen) {
      const fetchData = async () => {
        try {
          const profile = await axiosInstance.get("/users/profile");
          const userId = profile.data.userId;

          const accRes = await axiosInstance.get(`/accounts/user/${userId}`);
          const catRes = await axiosInstance.get(`/categories/user/${userId}`);

          setAccounts(accRes.data);
          setCategories(catRes.data);

          if (initialData) {
            setForm({
              ...initialData,
              isRecurring: initialData.isRecurring || false,
              recurringInterval: initialData.recurringInterval || "",
              nextDueDate: initialData.nextDueDate || "",
            });
          } else {
            setForm({
              transactionType: "INCOME",
              accountId: "",
              toAccountId: "",
              categoryId: "",
              amount: "",
              date: "",
              description: "",
              paymentMethod: "CASH",
              status: "PENDING",
              isRecurring: false,
              recurringInterval: "",
              nextDueDate: "",
            });
          }
        } catch (err) {
          console.error("❌ Error fetching form data:", err);
        }
      };

      fetchData();
    }
  }, [isOpen, initialData]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
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

      if (form.transactionType === "CREDIT_CARD_PAYMENT") {
        await axiosInstance.post(
          `/transactions/withdraw/${userId}/${form.accountId}`,
          payload
        );

        await axiosInstance.post(
          `/transactions/withdraw/${userId}/${form.toAccountId}`,
          payload
        );

        alert("✅ Credit Card Payment processed.");
      } else if (form.transactionType === "INCOME") {
        await axiosInstance.post(
          `/transactions/deposit/${userId}/${form.accountId}`,
          payload
        );
        alert("✅ Income recorded.");
      } else if (form.transactionType === "EXPENSE") {
        await axiosInstance.post(
          `/transactions/withdraw/${userId}/${form.accountId}`,
          payload
        );
        alert("✅ Expense recorded.");
      } else if (form.transactionType === "TRANSFER") {
        await axiosInstance.post(
          `/transactions/transfer/${userId}/${form.accountId}/${form.toAccountId}`,
          payload
        );
        alert("✅ Transfer successful.");
      } else if (form.transactionType === "RECURRING") {
        await axiosInstance.post(`/transactions/recurring`, payload);
        alert("✅ Recurring transaction created.");
      }
    } catch (err) {
      console.error("❌ Error submitting transaction:", err);
      alert("❌ Failed to process transaction.");
    }

    onClose();
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
                        {acc.institutionName} ({acc.name})
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

// import { useState, useEffect } from "react";
// import axiosInstance from "../service/axiosInstance";
// import "../styles/transaction.css";

// const STATUSES = ["PENDING", "COMPLETED", "FAILED"];
// const INTERVALS = ["DAILY", "WEEKLY", "MONTHLY", "YEARLY"];

// const TRANSACTION_TYPES = [
//   "INCOME",
//   "EXPENSE",
//   "TRANSFER",
//   "CREDIT_CARD_PAYMENT",
//   "RECURRING",
// ];

// const PAYMENT_METHODS = [
//   "CASH",
//   "BANK_TRANSFER",
//   "DEBIT_CARD",
//   "CREDIT_CARD",
//   "DIRECT_DEPOSIT",
//   "PAYPAL",
//   "OTHER",
// ];

// const TransactionForm = ({ isOpen, onClose, onSubmit, initialData }) => {
//   const [form, setForm] = useState({
//     transactionType: "INCOME",
//     accountId: "",
//     toAccountId: "",
//     categoryId: "",
//     amount: "",
//     date: "",
//     description: "",
//     paymentMethod: "CASH",
//     status: "PENDING",
//     isRecurring: false,
//     recurringInterval: "",
//     nextDueDate: "",
//   });

//   const [accounts, setAccounts] = useState([]);
//   const [categories, setCategories] = useState([]);

//   // 🔁 Fetch user profile + data on open
//   useEffect(() => {
//     if (isOpen) {
//       const fetchData = async () => {
//         try {
//           const profileRes = await axiosInstance.get("/users/profile");
//           const userId = profileRes.data.userId;

//           const accRes = await axiosInstance.get(`/accounts/user/${userId}`);
//           const catRes = await axiosInstance.get(`/categories/user/${userId}`);

//           setAccounts(accRes.data);
//           setCategories(catRes.data);

//           // Edit mode
//           if (initialData) {
//             setForm({
//               ...initialData,
//               isRecurring: initialData.isRecurring || false,
//               recurringInterval: initialData.recurringInterval || "",
//               nextDueDate: initialData.nextDueDate || "",
//             });
//           } else {
//             // Reset form for new transaction
//             setForm({
//               transactionType: "INCOME",
//               accountId: "",
//               toAccountId: "",
//               categoryId: "",
//               amount: "",
//               date: "",
//               description: "",
//               paymentMethod: "CASH",
//               status: "PENDING",
//               isRecurring: false,
//               recurringInterval: "",
//               nextDueDate: "",
//             });
//           }
//         } catch (err) {
//           console.error("❌ Error fetching transaction form data:", err);
//         }
//       };

//       fetchData();
//     }
//   }, [isOpen, initialData]);

//   const handleChange = (e) => {
//     const { name, value, type, checked } = e.target;
//     setForm((prev) => ({
//       ...prev,
//       [name]: type === "checkbox" ? checked : value,
//     }));
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     if (form.transactionType === "CREDIT_CARD_PAYMENT") {
//       try {
//         const profile = await axiosInstance.get("/users/profile");
//         const userId = profile.data.userId;

//         const payload = {
//           ...form,
//           amount: parseFloat(form.amount),
//           description: form.description || "Credit Card Payment",
//         };

//         // 1. Withdraw from Bank (fromAccount)
//         await axiosInstance.post(
//           `/transactions/withdraw/${userId}/${form.accountId}`,
//           payload
//         );

//         // 2. Withdraw from Credit Card (to reduce liability)
//         await axiosInstance.post(
//           `/transactions/withdraw/${userId}/${form.toAccountId}`,
//           payload
//         );

//         alert("✅ Credit Card Payment processed successfully!");
//       } catch (err) {
//         console.error("❌ Error processing credit card payment:", err);
//         alert("❌ Failed to process credit card payment.");
//       }
//     } else {
//       onSubmit?.(form); // Pass back to main logic for normal types
//     }

//     onClose();
//   };

//   if (!isOpen) return null;

//   return (
//     <div className="modal-overlay">
//       <div className="modal">
//         <h2>{initialData ? "Edit Transaction" : "Add Transaction"}</h2>
//         <form onSubmit={handleSubmit}>
//           <table className="form-table">
//             <tbody>
//               <tr>
//                 <td>
//                   <label>Type:</label>
//                 </td>
//                 <td>
//                   <select
//                     name="transactionType"
//                     value={form.transactionType}
//                     onChange={handleChange}
//                   >
//                     {TRANSACTION_TYPES.map((t) => (
//                       <option key={t}>{t}</option>
//                     ))}
//                   </select>
//                 </td>
//                 <td>
//                   <label>Amount:</label>
//                 </td>
//                 <td>
//                   <input
//                     type="number"
//                     name="amount"
//                     value={form.amount}
//                     onChange={handleChange}
//                     required
//                   />
//                 </td>
//               </tr>

//               <tr>
//                 <td>
//                   <label>Account:</label>
//                 </td>
//                 <td>
//                   <select
//                     name="accountId"
//                     value={form.accountId}
//                     onChange={handleChange}
//                     required
//                   >
//                     <option value="">Select Account</option>
//                     {accounts.map((acc) => (
//                       <option key={acc.accountId} value={acc.accountId}>
//                         {acc.institutionName} ({acc.name})
//                       </option>
//                     ))}
//                   </select>
//                 </td>

//                 {["TRANSFER", "CREDIT_CARD_PAYMENT"].includes(
//                   form.transactionType
//                 ) && (
//                   // {form.transactionType === "TRANSFER" && (
//                   <>
//                     <td>
//                       <label>To Account:</label>
//                     </td>
//                     <td>
//                       <select
//                         name="toAccountId"
//                         value={form.toAccountId}
//                         onChange={handleChange}
//                         required
//                       >
//                         <option value="">Select Destination</option>
//                         {accounts.map((acc) => (
//                           <option key={acc.accountId} value={acc.accountId}>
//                             {acc.institutionName} ({acc.name})
//                           </option>
//                         ))}
//                       </select>
//                     </td>
//                   </>
//                 )}
//               </tr>

//               <tr>
//                 <td>
//                   <label>Category:</label>
//                 </td>
//                 <td>
//                   <select
//                     name="categoryId"
//                     value={form.categoryId}
//                     onChange={handleChange}
//                     required
//                   >
//                     <option value="">Select Category</option>
//                     {categories.map((cat) => (
//                       <option key={cat.categoryId} value={cat.categoryId}>
//                         {cat.name} ({cat.type})
//                       </option>
//                     ))}
//                   </select>
//                 </td>

//                 <td>
//                   <label>Date:</label>
//                 </td>
//                 <td>
//                   <input
//                     type="datetime-local"
//                     name="date"
//                     value={form.date}
//                     onChange={handleChange}
//                     required
//                   />
//                 </td>
//               </tr>

//               <tr>
//                 <td>
//                   <label>Payment:</label>
//                 </td>
//                 <td>
//                   <select
//                     name="paymentMethod"
//                     value={form.paymentMethod}
//                     onChange={handleChange}
//                   >
//                     {PAYMENT_METHODS.map((p) => (
//                       <option key={p}>{p}</option>
//                     ))}
//                   </select>
//                 </td>

//                 <td>
//                   <label>Status:</label>
//                 </td>
//                 <td>
//                   <select
//                     name="status"
//                     value={form.status}
//                     onChange={handleChange}
//                   >
//                     {STATUSES.map((s) => (
//                       <option key={s}>{s}</option>
//                     ))}
//                   </select>
//                 </td>
//               </tr>

//               <tr>
//                 <td>
//                   <label>Description:</label>
//                 </td>
//                 <td colSpan="3">
//                   <input
//                     name="description"
//                     value={form.description}
//                     onChange={handleChange}
//                   />
//                 </td>
//               </tr>

//               <tr>
//                 <td>
//                   <label>Recurring:</label>
//                 </td>
//                 <td>
//                   <input
//                     type="checkbox"
//                     name="isRecurring"
//                     checked={form.isRecurring}
//                     onChange={handleChange}
//                   />
//                 </td>

//                 {form.isRecurring && (
//                   <>
//                     <td>
//                       <label>Interval:</label>
//                     </td>
//                     <td>
//                       <select
//                         name="recurringInterval"
//                         value={form.recurringInterval}
//                         onChange={handleChange}
//                       >
//                         <option value="">Select</option>
//                         {INTERVALS.map((i) => (
//                           <option key={i}>{i}</option>
//                         ))}
//                       </select>
//                     </td>
//                   </>
//                 )}
//               </tr>

//               {form.isRecurring && (
//                 <tr>
//                   <td>
//                     <label>Next Due Date:</label>
//                   </td>
//                   <td colSpan="3">
//                     <input
//                       type="datetime-local"
//                       name="nextDueDate"
//                       value={form.nextDueDate}
//                       onChange={handleChange}
//                     />
//                   </td>
//                 </tr>
//               )}

//               <tr>
//                 <td colSpan="4" className="form-buttons-row">
//                   <div className="form-buttons">
//                     <button type="submit" className="save-btn">
//                       Save
//                     </button>
//                     <button
//                       type="button"
//                       onClick={onClose}
//                       className="cancel-btn"
//                     >
//                       Cancel
//                     </button>
//                   </div>
//                 </td>
//               </tr>
//             </tbody>
//           </table>
//         </form>
//       </div>
//     </div>
//   );
// };

// export default TransactionForm;
