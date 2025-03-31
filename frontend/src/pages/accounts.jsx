import { useState, useEffect } from "react";
import axiosInstance from "../service/axiosInstance";
import AccountForm from "../pages/accountForm.jsx";
import AllAccountsModal from "../pages/accoundDetails.jsx";

import "../styles/accounts.css";

const Accounts = () => {
  const [accounts, setAccounts] = useState([]);
  const [expandedAccount, setExpandedAccount] = useState(null);
  const [userId, setUserId] = useState(null);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showAllModal, setShowAllModal] = useState(false);
  const [editAccount, setEditAccount] = useState(null);

  // ✅ Fetch user profile
  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        const response = await axiosInstance.get("/users/profile");
        setUserId(response.data.userId);
      } catch (error) {
        console.error(
          "❌ Error fetching user profile:",
          error.response?.data || error.message
        );
      }
    };
    fetchUserProfile();
  }, []);

  // ✅ Fetch accounts
  const fetchAccounts = async () => {
    try {
      const response = await axiosInstance.get(`/accounts/user/${userId}`);
      setAccounts(response.data);
    } catch (error) {
      console.error(
        "❌ Error fetching accounts:",
        error.response?.data || error.message
      );
    }
  };

  useEffect(() => {
    if (userId) fetchAccounts();
  }, [userId]);

  // ✅ Toggle card details
  const toggleDetails = (accountId) => {
    setExpandedAccount((prev) => (prev === accountId ? null : accountId));
  };

  // ✅ Add or Edit Account
  const handleSaveAccount = async (formData) => {
    try {
      if (editAccount) {
        await axiosInstance.put(`/accounts/${editAccount.accountId}`, formData);
      } else {
        await axiosInstance.post(`/accounts/create/${userId}`, formData);
      }

      setShowAddModal(false);
      setEditAccount(null);
      fetchAccounts();
    } catch (error) {
      console.error(
        "❌ Error saving account:",
        error.response?.data || error.message
      );
    }
  };

  return (
    <div className="accounts-container">
      <h2 className="header">My Accounts</h2>

      <div className="account-actions">
        <button
          onClick={() => {
            setShowAddModal(false); // Close if open
            setEditAccount(null); // Clear old data
            setTimeout(() => {
              setShowAddModal(true); // Then reopen cleanly
            }, 0); // minimal delay ensures form resets
          }}
        >
          Add New
        </button>

        {/* <button
          onClick={() => {
            setEditAccount(null);
            setShowAddModal(true);
          }}
        >
          Add New
        </button> */}
        <button onClick={() => setShowAllModal(true)}>View All</button>
      </div>

      <div className="account-list">
        {accounts.length > 0 ? (
          accounts.map((account) => (
            <div className="account-card" key={account.accountId}>
              <h3>{account.name}</h3>
              <p>
                Balance: {account.currency} {account.balance.toFixed(2)}
              </p>
              <div className="card-btn">
                <button onClick={() => toggleDetails(account.accountId)}>
                  {expandedAccount === account.accountId
                    ? "Hide Details"
                    : "View Details"}
                </button>
              </div>

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

      {/* ✅ Account Form Modal */}
      <AccountForm
        isOpen={showAddModal}
        onClose={() => {
          setEditAccount(null);
          setShowAddModal(false);
        }}
        onSubmit={handleSaveAccount}
        initialData={editAccount}
      />

      {/* ✅ All Accounts Modal */}
      <AllAccountsModal
        isOpen={showAllModal}
        onClose={() => setShowAllModal(false)}
        userId={userId}
        onEdit={(acc) => {
          setEditAccount(acc);
          setShowAllModal(false);
          setShowAddModal(true);
        }}
      />
    </div>
  );
};

export default Accounts;

// import { useState, useEffect } from "react";
// import axiosInstance from "../service/axiosInstance";
// import AccountForm from "../pages/accountForm.jsx";
// import AllAccountsModal from "../pages/accoundDetails.jsx";

// import "../styles/accounts.css"; // ✅ Import CSS file

// const Accounts = () => {
//   const [accounts, setAccounts] = useState([]); // Store user accounts
//   const [expandedAccount, setExpandedAccount] = useState(null); // Track expanded details
//   const [userId, setUserId] = useState(null); // ✅ Store logged-in user ID
//   const [showAddModal, setShowAddModal] = useState(false); // Account form
//   const [showAllModal, setShowAllModal] = useState(false);
//   const [editAccount, setEditAccount] = useState(null); // for form reuse

//   // ✅ Fetch user profile to get userId
//   useEffect(() => {
//     const fetchUserProfile = async () => {
//       try {
//         const response = await axiosInstance.get("/users/profile");
//         setUserId(response.data.userId); // ✅ Store userId
//       } catch (error) {
//         console.error(
//           "❌ Error fetching user profile:",
//           error.response?.data || error.message
//         );
//       }
//     };
//     fetchUserProfile();
//   }, []);

//   // ✅ Fetch accounts after userId is available
//   useEffect(() => {
//     if (!userId) return; // ✅ Wait for userId before fetching accounts

//     const fetchAccounts = async () => {
//       try {
//         const response = await axiosInstance.get(`/accounts/user/${userId}`); // ✅ Fetch accounts with userId
//         setAccounts(response.data); // ✅ Store fetched accounts
//       } catch (error) {
//         console.error(
//           "❌ Error fetching accounts:",
//           error.response?.data || error.message
//         );
//       }
//     };
//     fetchAccounts();
//   }, [userId]); // ✅ Run when userId is set

//   // ✅ Toggle Details View
//   const toggleDetails = (accountId) => {
//     setExpandedAccount((prev) => (prev === accountId ? null : accountId));
//   };

//   const handleSaveAccount = async (formData) => {
//     try {
//       if (editAccount) {
//         // 👈 Edit mode
//         await axiosInstance.put(`/accounts/${editAccount.accountId}`, formData);
//       } else {
//         // 👈 Add mode
//         await axiosInstance.post(`/accounts/create/${userId}`, formData);
//       }

//       setShowAddModal(false);
//       setEditAccount(null);

//       // 🔄 Refresh cards
//       const response = await axiosInstance.get(`/accounts/user/${userId}`);
//       setAccounts(response.data);
//     } catch (error) {
//       console.error(
//         "❌ Error saving account:",
//         error.response?.data || error.message
//       );
//     }
//   };

//   //  Account Form handle
//   const handleAddAccount = async (formData) => {
//     try {
//       // 👇 Correct endpoint with userId in the URL
//       await axiosInstance.post(`/accounts/create/${userId}`, formData);

//       setShowAddModal(false);

//       // 🔄 Refresh accounts list
//       const response = await axiosInstance.get(`/accounts/user/${userId}`);
//       setAccounts(response.data);
//     } catch (error) {
//       console.error(
//         "❌ Error adding account:",
//         error.response?.data || error.message
//       );
//     }
//   };

//   return (
//     <div className="accounts-container">
//       <h2 className="header">My Accounts</h2>
//       {/* Account fomr */}
//       <div className="account-actions">
//         <button onClick={() => setShowAddModal(true)}>Add New</button>
//         {/* Placeholder for View All button */}
//         <button onClick={() => setShowAllModal(true)}>View All</button>
//       </div>
//       {/* ✅ Display account cards */}
//       <div className="account-list">
//         {accounts.length > 0 ? (
//           accounts.map((account) => (
//             <div className="account-card" key={account.accountId}>
//               <h3>{account.name}</h3>
//               <p>
//                 Balance: {account.currency} {account.balance.toFixed(2)}
//               </p>
//               <div className="card-btn">
//                 <button onClick={() => toggleDetails(account.accountId)}>
//                   {expandedAccount === account.accountId
//                     ? "Hide Details"
//                     : "View Details"}
//                 </button>
//               </div>

//               {/* ✅ Show full details when expanded */}
//               {expandedAccount === account.accountId && (
//                 <div className="account-details">
//                   <table>
//                     <tbody>
//                       <tr>
//                         <td>
//                           <strong>Institution:</strong>
//                         </td>
//                         <td>{account.institutionName || "N/A"}</td>
//                       </tr>
//                       <tr>
//                         <td>
//                           <strong>Account Number:</strong>
//                         </td>
//                         <td>{account.accountNumber || "N/A"}</td>
//                       </tr>
//                       <tr>
//                         <td>
//                           <strong>Interest Rate:</strong>
//                         </td>
//                         <td>
//                           {account.interestRate
//                             ? `${account.interestRate}%`
//                             : "N/A"}
//                         </td>
//                       </tr>
//                       <tr>
//                         <td>
//                           <strong>Created On:</strong>
//                         </td>
//                         <td>
//                           {new Date(account.dateCreated).toLocaleDateString()}
//                         </td>
//                       </tr>
//                       <tr>
//                         <td>
//                           <strong>Last Updated:</strong>
//                         </td>
//                         <td>
//                           {new Date(account.dateUpdated).toLocaleDateString()}
//                         </td>
//                       </tr>
//                     </tbody>
//                   </table>
//                 </div>
//               )}
//             </div>
//           ))
//         ) : (
//           <p>No accounts found. Create one to get started!</p>
//         )}
//       </div>
//       {/* Account form */}
//       <AccountForm
//         isOpen={showAddModal}
//         onClose={() => setShowAddModal(false)}
//         onSubmit={handleAddAccount}
//         initialData={null}
//       />
//       {/*  All Accounts Modal  +++++++++++++++++++++++*/};
//       <AllAccountsModal
//         isOpen={showAllModal}
//         onClose={() => setShowAllModal(false)}
//         userId={userId}
//         onEdit={(acc) => {
//           setEditAccount(acc);
//           setShowAllModal(false);
//           setShowAddModal(true);
//         }}
//       />
//     </div>
//   );
// };

// export default Accounts;
