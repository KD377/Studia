import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../App.css';
import Filters from '../components/Filters'

const ProductTable = () => {
    const [products, setProducts] = useState([]);
    const [addedProducts, setAddedProducts] = useState([]);
    const [filterName, setFilterName] = useState('');
    const [filterCategory, setFilterCategory] = useState('All');
    const [categories, setCategories] = useState(['All']);

    const navigate = useNavigate();

    useEffect(() => {
        axios.get('http://localhost:4000/products')
            .then((response) => setProducts(response.data))
            .catch((error) => console.error('Error fetching products:', error));
    }, []);

    useEffect(() => {
        axios.get('http://localhost:4000/categories')
            .then((response) => {
                const categoryObjects = response.data;
                const categoryNames = categoryObjects.map(category => category.name);

                setCategories(['All', ...categoryNames]);
            })
            .catch((error) => console.error('Error fetching categories:', error));
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
        (filterCategory === 'All' || product.category === filterCategory)
    );

    return (
        <div>
            <Filters
                filterName={filterName}
                setFilterName={setFilterName}
                filterCategory={filterCategory}
                setFilterCategory={setFilterCategory}
                categories={categories}
            />
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
