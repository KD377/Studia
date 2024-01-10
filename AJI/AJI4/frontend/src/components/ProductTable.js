import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../App.css';

const ProductTable = () => {
  const [products, setProducts] = useState([]);
  const [addedProducts, setAddedProducts] = useState([]);
  const [filterName, setFilterName] = useState('');
  const [filterCategory, setFilterCategory] = useState('None');
  const navigate = useNavigate();

  useEffect(() => {
      axios.get('http://localhost:4000/products')
          .then((response) => setProducts(response.data))
          .catch((error) => console.error('Error fetching products:', error));
  }, []);

  const handleButtonClick = (product) => {
      const productId = product.id;

      if (addedProducts.some((addedProduct) => addedProduct.id === productId)) {
          setAddedProducts(addedProducts.filter((addedProduct) => addedProduct.id !== productId));
      } else {
          setAddedProducts([...addedProducts, product]);
      }
  };

  const handleCheckoutClick = () => {
      navigate('/checkout', { state: { addedProducts } });
  };

  const filteredProducts = products.filter((product) =>
      (filterName === '' || product.name.toLowerCase().includes(filterName.toLowerCase())) &&
      (filterCategory === 'None' || product.category === filterCategory)
  );

  const categories = ['None', ...Array.from(new Set(products.map((product) => product.category)))];

  return (
      <div>
          <div className="filters">
              <label>
                  Filter by Name:
                  <input
                      type="text"
                      value={filterName}
                      onChange={(e) => setFilterName(e.target.value)}
                  />
              </label>
              <label>
                  Filter by Category:
                  <select
                      value={filterCategory}
                      onChange={(e) => setFilterCategory(e.target.value)}
                  >
                      {categories.map((category) => (
                          <option key={category} value={category}>{category}</option>
                      ))}
                  </select>
              </label>
          </div>
          <table className="table table-striped table-dark">
          
              <tbody>
                  {filteredProducts.map((product) => (
                      <tr key={product.id}>
                        <td>{product.name}</td>
                        <td>{product.description}</td>
                        <td>{product.price}</td>
                        <td>{product.weight}</td>
                        <td>{product.category}</td>
                          <td className='text-center'>
                              <button
                                  style={{ width: '100px' }}
                                  className={`btn ${addedProducts.includes(product) ? 'btn-danger' : 'btn-success'}`}
                                  onClick={() => handleButtonClick(product)}
                              >
                                  {addedProducts.includes(product) ? 'Remove' : 'Add'}
                              </button>
                          </td>
                      </tr>
                  ))}
              </tbody>
          </table>
          {addedProducts.length > 0 && (
              <div className='text-center mt-5'>
                  <button className="btn btn-dark" style={{ width: '200px', fontSize: '1.5em' }}
                      onClick={handleCheckoutClick}>
                      Checkout
                  </button>
              </div>
          )}
      </div>
  );
};

export default ProductTable;
