import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import '../App.css';
import Filters from "../components/Filters";

const ProductTable = () => {
    const [products, setProducts] = useState([]);
    const [filterName, setFilterName] = useState('');
    const [filterCategory, setFilterCategory] = useState('All');
    const [categories, setCategories] = useState(['All']);

    const navigate = useNavigate();
    const location = useLocation();
    const { state } = location;


    useEffect(() => {
        axios.get('http://localhost:4000/products')
            .then((response) => setProducts(response.data))
            .catch((error) => console.error('Error fetching products:', error));
    }, [state]);

    useEffect(() => {
        axios.get('http://localhost:4000/categories')
            .then((response) => {
                const categoryObjects = response.data;
                const categoryNames = categoryObjects.map(category => category.name);

                setCategories(['All', ...categoryNames]);
            })
            .catch((error) => console.error('Error fetching categories:', error));
    }, []);

    const filteredProducts = products.filter((product) =>
        (filterName === '' || product.name.toLowerCase().includes(filterName.toLowerCase())) &&
        (filterCategory === 'All' || product.category === filterCategory)
    );

    const handleButtonClick = (product) => {
        navigate('/edit',{state: {product}});
    }


    return (
        <div className='container'>
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
                                    className={`btn btn-warning`}
                                    onClick={() => handleButtonClick(product)}
                                >
                                    Edit
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ProductTable;
