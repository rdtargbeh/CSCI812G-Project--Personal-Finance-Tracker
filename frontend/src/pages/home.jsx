import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const [showRegister, setShowRegister] = useState(false);
  const navigate = useNavigate();

  return (
    <div style={styles.page}>
      <h1>Welcome to your one stop Personal Finance Tracker 💰</h1>;
      <p>Track your income, expenses, and savings easily.</p>
    </div>
  );
};

const styles = {
  page: {
    padding: "20px",
    textAlign: "center",
  },
};

export default Home;
