import { useState, useEffect } from "react";
import axiosInstance from "../service/axiosInstance";
import { useNavigate } from "react-router-dom"; // ✅ Import this at the top

const Dashboard = () => {
  const [userData, setUserData] = useState(null);
  const [Loading, setLoading] = useState(true);
  const navigate = useNavigate(); // ✅ Define navigate function
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        console.log("Fetching user data...");

        const response = await axiosInstance.get("/users/profile"); // ✅ Uses axiosInstance (already has token)

        console.log("User Data Received:", response.data);
        setUserData(response.data); // ✅ Store user data in state
      } catch (error) {
        console.error(
          "Error fetching user data:",
          error.response?.data || error.message
        );
        setError("Failed to load user data.");
      } finally {
        setLoading(false); // Ensure loading stops
      }
    };

    fetchUserData();
  }, []);

  return (
    <div>
      <h2>Welcome to Your Dashboard</h2>
      <p>Here you can see your financial stats.</p>
    </div>
  );
};

export default Dashboard;
