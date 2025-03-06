import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/home"; // import home page from pages
import About from "./pages/about"; // import about page from pages
import Navbar from "./components/navbar"; // ✅ Import Navbar

const App = () => {
  return (
    <Router>
      <Navbar /> {/* ✅ Add Navbar */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />{" "}
        {/* route or url of about apage */}
      </Routes>
    </Router>
  );
};

export default App;
