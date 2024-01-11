import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../App.css';

const ProductTable = () => {
    const [products, setProducts] = useState([]);
    const [orders, setOrders] = useState([]);
    const [selectedStatus, setSelectedStatus] = useState('2'); // Default to 'Approved'


    useEffect(() => {
        axios.get('http://localhost:4000/products')
            .then((response) => setProducts(response.data))
            .catch((error) => console.error('Error fetching products:', error));
    }, []);

    useEffect(() => {
        axios.get(`http://localhost:4000/orders/status/${selectedStatus}`)
            .then((response) => setOrders(response.data))
            .catch((error) => console.error('Error fetching orders:', error));
    }, [selectedStatus]);

    const date = (confirm_date) => {
        const date = new Date(confirm_date);
        const formattedDate = date.toLocaleDateString('en-US');
        return formattedDate;
    };

    return (
        <div className='container'>
            <div>
                <label>Select Order Status: </label>
                <select
                    value={selectedStatus}
                    onChange={(e) => setSelectedStatus(e.target.value)}
                >
                    <option value="1">Not Approved</option>
                    <option value="2">Approved</option>
                    <option value="3">Cancelled</option>
                    <option value="4">Completed</option>
                </select>
            </div>
            <table className="table table-striped table-dark">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Confirm Date</th>
                        <th>Username</th>
                        <th>Total Sum</th>
                    </tr>
                </thead>
                <tbody>
                    {orders.map((order) => {
                        const totalSum = order.products.reduce((sum, productId, index) => {
                            const product = products.find((product) => product.id === productId);
                            const quantity = order.quantities[index];
                            const productTotal = product ? product.price * quantity : 0;
                            return sum + productTotal;
                        }, 0);

                        return (
                            <tr key={order.order_id}>
                                <td>{order.order_id}</td>
                                <td>{date(order.confirm_date)}</td>
                                <td>{order.username}</td>
                                <td>{totalSum.toFixed(2)}</td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
};

export default ProductTable;
