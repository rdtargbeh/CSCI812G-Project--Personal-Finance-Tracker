import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/home";
import About from "./pages/about";
import Navbar from "./components/navbar";
import Login from "./pages/login";
import Register from "./pages/register";
import DashboardLayout from "./layouts/dashboardlayout";
import Transactions from "./pages/transactions";
import Loans from "./pages/loans";
import Dashboard from "./pages/dashboard"; // ✅ Add this if missing
import Accounts from "./pages/accounts";
import Savings from "./pages/savings";
import Investments from "./pages/investments";
import Reports from "./pages/reports";
import Profile from "./pages/profile";
import Subscriptions from "./pages/subscriptions";
import ProtectedRoute from "./routes/ProtectedRoute"; // ✅ Import Protected Route

const App = () => {
  return (
    <Router>
      <Navbar /> {/* ✅ Navbar stays for public pages */}
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Protected Dashboard Routes */}
        <Route element={<ProtectedRoute />}>
          <Route path="/dashboard" element={<DashboardLayout />}>
            <Route path="profile" element={<Profile />} />
            <Route path="/dashboard/profile" element={<Profile />} />
            <Route index element={<Dashboard />} />
            <Route path="transactions" element={<Transactions />} />
            <Route path="loans" element={<Loans />} />
            <Route path="accounts" element={<Accounts />} />
            <Route path="savings" element={<Savings />} />
            <Route path="investments" element={<Investments />} />
            <Route path="reports" element={<Reports />} />
            <Route path="subscriptions" element={<Subscriptions />} />
          </Route>
        </Route>
      </Routes>
    </Router>
  );
};
export default App;
