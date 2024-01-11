import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const ConfirmationPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const orderDetails = location.state;

  const handleReturnToMainPage = () => {
    navigate('/');
  };

  return (
    <div className="container mt-4">
      <div className='bg-dark text-white p-5 rounded shadow'>
        <h1>Order Confirmation</h1>
        <p>Order identity number: {orderDetails.order_id}</p>
        <p>Username: {orderDetails.username}</p>
        <p>Email: {orderDetails.email}</p>
        <p>Phone: {orderDetails.phone}</p>
        <p>Status: {orderDetails.status}</p>
        <p>Pieces: {orderDetails.pieces}</p>
      </div>
      <button className="btn btn-warning mt-5 mx-auto d-block" onClick={handleReturnToMainPage}>
        Return to Main Page
      </button>
    </div>
  );
};

export default ConfirmationPage;
