import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../App.css';

const ProductTable = () => {
    const [products, setProducts] = useState([]);
    const [orders, setOrders] = useState([]);
    const [update, setUpdate] = useState(false);

    useEffect(() => {
        axios.get('http://localhost:4000/products')
            .then((response) => setProducts(response.data))
            .catch((error) => console.error('Error fetching products:', error));
    }, [update]);

    useEffect(() => {
        axios.get('http://localhost:4000/orders/status/2')
            .then((response) => setOrders(response.data))
            .catch((error) => console.error('Error fetching orders:', error));
    }, [update]);

    const handleCancel = (orderId) => {
        const patchOperations = [
            { op: 'replace', path: '/order_status_id', value: 3 }
        ];

        axios.patch(`http://localhost:4000/orders/${orderId}`, patchOperations)
            .then((response) => {
                setUpdate(!update);
                alert(`Order ${orderId} has been marked as cancelled`);
                console.log(response);
            })
            .catch((error) => {
                alert(error.response.data.error);
            });

    };

    const handleComplete = (orderId) => {
        const patchOperations = [
            { op: 'replace', path: '/order_status_id', value: 4 }
        ];

        axios.patch(`http://localhost:4000/orders/${orderId}`, patchOperations)
            .then((response) => {
                setUpdate(!update);
                alert(`Order ${orderId} has been marked as completed`);
                console.log(response);
            })
            .catch((error) => {
                alert(error.response.data.error);
            });
    };


    const date = (confirm_date) => {
        const date = new Date(confirm_date);
        const formattedDate = date.toLocaleDateString('en-US');
        return formattedDate;
    }
    return (
        <div className='container'>
            <table className="table table-striped table-dark">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Confirm Date</th>
                        <th>Products</th>
                        <th>Total Sum</th>
                        <th>Actions</th>
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
                                <td>
                                    {order.products.map((productId, index) => (
                                        <span key={productId}>
                                            {products.find((product) => product.id === productId)?.name}
                                            ({order.quantities[index]})
                                            {index < order.products.length - 1 && ', '}
                                        </span>
                                    ))}
                                </td>
                                <td>{totalSum.toFixed(2)}</td>
                                <td>
                                    <button
                                        className="btn btn-danger"
                                        onClick={() => handleCancel(order.order_id)}
                                    >
                                        Cancel
                                    </button>
                                    <button
                                        className="btn btn-success ml-2"
                                        onClick={() => handleComplete(order.order_id)}
                                    >
                                        Complete
                                    </button>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
};

export default ProductTable;
