import React, { useState, useEffect } from 'react';

const ProductTable = ({ addedProducts, onQuantityChange, onDeleteProduct }) => {
  const [totalPrices, setTotalPrices] = useState({});
  const [totalSum, setTotalSum] = useState(0);

  useEffect(() => {
    const initialTotalPrices = {};
    addedProducts.forEach((product) => {
      initialTotalPrices[product.id] = product.price;
    });
    setTotalPrices(initialTotalPrices);
  }, [addedProducts]);

  useEffect(() => {
    // Calculate the sum of all total prices
    const sum = Object.values(totalPrices).reduce((acc, price) => acc + parseFloat(price), 0);
    setTotalSum(sum.toFixed(2));
  }, [totalPrices]);

  const handleQuantityChange = (productId, quantity) => {
    onQuantityChange(productId, quantity);

    const totalPrice = addedProducts.find((product) => product.id === productId).price * quantity;
    setTotalPrices((prevTotalPrices) => ({
      ...prevTotalPrices,
      [productId]: totalPrice.toFixed(2),
    }));
  };

  return (
    <div>
      <table className="table table-striped table-dark">
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Weight</th>
            <th>Price</th>
            <th>Category</th>
            <th>Total price</th>
            <th>Quantity</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {addedProducts.map((product) => (
            <tr key={product.id}>
              <td>{product.name}</td>
              <td>{product.description}</td>
              <td>{product.weight}</td>
              <td>{product.price}</td>
              <td>{product.category}</td>
              <td>{totalPrices[product.id]}</td>
              <td>
                <input
                  type="number"
                  className="form-control "
                  defaultValue={1}
                  min={1}
                  onChange={(e) => handleQuantityChange(product.id, parseInt(e.target.value, 10))}
                />
              </td>
              <td>
                <button className="btn btn-danger" onClick={() => onDeleteProduct(product.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div>
        <strong>Total Sum: {totalSum}</strong>
      </div>
    </div>
  );
};

export default ProductTable;
