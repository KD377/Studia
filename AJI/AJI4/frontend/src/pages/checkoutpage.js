import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import UserForm from '../components/UserForm';
import OrderTable from '../components/OrderedTable';

const CheckoutPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { addedProducts } = location.state;


  const initialQuantities = {};
  addedProducts.forEach((product) => {
    initialQuantities[product.id] = 1;
  });

  const [quantities, setQuantities] = useState(initialQuantities);
  const [updatedProducts, setUpdatedProducts] = useState([...addedProducts]);

  const handleUserFormSubmit = (userData) => {

    // Validate user data
    if (!userData.userName || !userData.email || !userData.phoneNumber) {
      alert('Please fill in all required fields.');
      return;
    }

    // Validate email format
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(userData.email)) {
      alert('Please enter a valid email address.');
      return;
    }

    // Validate phone number format (9 digits)
    const phoneRegex = /^\d{9}$/;
    if (!phoneRegex.test(userData.phoneNumber)) {
      alert('Please enter a valid 9-digit phone number.');
      return;
    }

    const orderData = {
      confirm_date: '2024-01-01',
      order_status: 'APPROVED',
      username: userData.userName,
      email: userData.email,
      phone_number: userData.phoneNumber,
      products: updatedProducts.map((product) => product.name),
      quantities: Object.values(quantities),
    };

  
    axios.post('http://localhost:4000/orders', orderData)
      .then((response) => {
        console.log('Order placed successfully:', response.data);
        navigate('/confirm', { state: response.data });
      })
      .catch((error) => {
        alert(error);
      });
  };

  const handleQuantityChange = (productId, quantity) => {
    setQuantities({ ...quantities, [productId]: quantity });
  };

  const handleDeleteProduct = (productId) => {
    const updatedProductsAfterDelete = updatedProducts.filter((product) => product.id !== productId);
    setUpdatedProducts(updatedProductsAfterDelete);
  };

  return (
    <div className="container mt-4 bg-dark rounded shadow p-4">
      <h1 className='text-white text-center'>Summary</h1>
      <div className="row">
        <div className="col-md-4">
          <UserForm onSubmit={handleUserFormSubmit} />
        </div>
        <div className="col-md-8">
          <OrderTable
            addedProducts={updatedProducts}
            onQuantityChange={handleQuantityChange}
            onDeleteProduct={handleDeleteProduct}
          />
        </div>
      </div>
    </div>
  );
};

export default CheckoutPage;
