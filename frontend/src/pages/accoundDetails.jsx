import { useEffect, useState } from "react";
import axiosInstance from "../service/axiosInstance";
import "../styles/accounts.css";

const AllAccountsModal = ({ isOpen, onClose, userId, onEdit }) => {
  const [allAccounts, setAllAccounts] = useState([]);

  useEffect(() => {
    if (!userId || !isOpen) return;

    const fetchAll = async () => {
      try {
        const res = await axiosInstance.get(`/accounts/user/${userId}/all`);
        setAllAccounts(res.data);
      } catch (err) {
        console.error(
          "❌ Error fetching all accounts:",
          err.response?.data || err.message
        );
      }
    };

    fetchAll();
  }, [userId, isOpen]);

  const handleDelete = async (id) => {
    await axiosInstance.delete(`/accounts/${id}`);
    const res = await axiosInstance.get(`/accounts/user/${userId}`);
    setAllAccounts(res.data);
  };

  const handleRestore = async (id) => {
    await axiosInstance.put(`/accounts/${id}/restore`);
    const res = await axiosInstance.get(`/accounts/user/${userId}`);
    setAllAccounts(res.data);
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modals">
        <button onClick={onClose} className="close-btn">
          X
        </button>
        <h2 className="header">All Accounts</h2>
        <table className="accounts-table">
          <thead>
            <tr>
              <th>Title</th>
              <th>Type</th>
              <th>Balance</th>
              <th>Institution</th>
              <th>Account No.</th>
              <th>Interest</th>
              <th>Created</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {allAccounts.map((acc) => (
              <tr
                key={acc.accountId}
                className={acc.deleted ? "deleted-row" : ""}
              >
                <td>{acc.name}</td>
                <td>{acc.type}</td>
                <td>
                  {acc.currency} {acc.balance}
                </td>
                <td>{acc.institutionName}</td>
                <td>{acc.accountNumber}</td>
                <td>{acc.interestRate}%</td>
                <td>{new Date(acc.dateCreated).toLocaleDateString()}</td>
                <td>
                  {!acc.deleted ? (
                    <>
                      <button onClick={() => onEdit(acc)}>Edit</button>
                      <button onClick={() => handleDelete(acc.accountId)}>
                        Delete
                      </button>
                    </>
                  ) : (
                    <button onClick={() => handleRestore(acc.accountId)}>
                      Restore
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <div className="form-buttons"></div>
      </div>
    </div>
  );
};

export default AllAccountsModal;
