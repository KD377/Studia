import React, { useState } from 'react';

const UserForm = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    userName: '',
    phoneNumber: '',
    email: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFormSubmit = (e) => {
    e.preventDefault(); // Prevent the default form submission behavior
    onSubmit(formData); // Call the parent component's onSubmit function with the form data
  };

  return (
    <form onSubmit={handleFormSubmit} className='text-white'>
      <div className="mb-3">
        <label htmlFor="userName" className="form-label">User Name</label>
        <input
          type="text"
          className="form-control"
          id="userName"
          name="userName"
          value={formData.userName}
          onChange={handleInputChange}
        />
      </div>
      <div className="mb-3">
        <label htmlFor="phoneNumber" className="form-label">Phone Number</label>
        <input
          type="text"
          className="form-control"
          id="phoneNumber"
          name="phoneNumber"
          value={formData.phoneNumber}
          onChange={handleInputChange}
        />
      </div>
      <div className="mb-3">
        <label htmlFor="email" className="form-label">Email</label>
        <input
          type="email"
          className="form-control"
          id="email"
          name="email"
          value={formData.email}
          onChange={handleInputChange}
        />
      </div>
      <button type="submit" className="btn btn-success py-2">
        Place Order
      </button>
    </form>
  );
};

export default UserForm;
