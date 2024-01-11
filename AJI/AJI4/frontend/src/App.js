import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import MainPage from './pages/mainpage';
import CheckoutPage from './pages/checkoutpage';
import ConfirmPage from './pages/confirmpage';
import './App.css';
import ManagePage from './pages/managepage';
import EditProductPage from './pages/editproductspage';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/checkout" element={<CheckoutPage />} />
        <Route path="/confirm" element={<ConfirmPage />} />
        <Route path="/manage" element={<ManagePage />} />
        <Route path="/edit" element={<EditProductPage />} />
      </Routes>
    </Router>
  );
};

export default App;
