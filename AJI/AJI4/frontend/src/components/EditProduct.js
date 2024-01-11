import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';


const EditProduct = ({ product }) => {
    const navigate = useNavigate();
    const [editedProduct, setEditedProduct] = useState(product || {});
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        setEditedProduct(product);
    }, [product]);

    useEffect(() => {
        axios.get('http://localhost:4000/categories')
            .then((response) => {
                const categoryObjects = response.data;
                const categoryNames = categoryObjects.map(category => category.name);

                setCategories([...categoryNames]);
            })
            .catch((error) => console.error('Error fetching categories:', error));
    }, []);

    const handleChange = (e) => {
        setEditedProduct({
            ...editedProduct,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = () => {
        axios.put(`http://localhost:4000/products/${editedProduct.id}`, editedProduct)
            .then((response) => {
                navigate('/manage', { state: response.data });
            })
            .catch((error) => {
                alert(error.response.data.error);
            });
        navigate('/manage');
    };
    return (
        <div className='container bg-dark text-white py-4'>
            <h2 className='text-center'>Edit Product: {product.name} id: {product.id}</h2>
            <div className='col-md-6 mx-auto'>
                <form onSubmit={handleSubmit}>
                    <div className='form-group p-2'>
                        <label htmlFor="nameInput">Name</label>
                        <input type="text" name="name" value={editedProduct.name || ''}
                            onChange={handleChange} className="form-control" id="nameInput" />
                    </div>
                    <div className='form-group p-2'>
                        <label htmlFor="descriptionInput">Description</label>
                        <textarea name="description" value={editedProduct.description || ''}
                            onChange={handleChange} className="form-control" id="descriptionInput" rows="3" />
                    </div>
                    <div className='form-group p-2'>
                        <label htmlFor="priceInput">Price</label>
                        <input type="number" name="price" value={editedProduct.price || ''}
                            onChange={handleChange} className="form-control" id="priceInput" />
                    </div>
                    <div className='form-group p-2'>
                        <label htmlFor="weightInput">Weight</label>
                        <input type="number" name="weight" value={editedProduct.weight || ''}
                            onChange={handleChange} className="form-control" id="weightInput" />
                    </div>
                    <div className="form-group px-2 pt-2">
                        <label htmlFor="exampleFormControlSelect1">Category:</label>
                        <select
                            className="form-control"
                            id="exampleFormControlSelect1"
                            name="category"
                            value={editedProduct.category || ''}
                            onChange={handleChange}
                        >
                            {categories.map((category) => (
                                <option key={category} value={category}>
                                    {category}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className='row justify-content-between'>
                        <div className='col-md-4'>
                            <button className='btn btn-success mt-4 ' style={{ width: 150 }} type="submit">Save Changes</button>
                        </div>
                        <div className='col-md-4'>
                            <a href='/manage'><button className='btn btn-secondary mt-4 ' style={{ width: 150 }}>Back</button></a>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    );
};

export default EditProduct;
