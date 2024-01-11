import React, { useState } from 'react';
import ManageTable from '../components/ManageTable';
import UnrealisedOrders from '../components/UnrealisedOrders';
import NavigationBar from '../components/NavigationBar';
import Orders from '../components/Orders';

const ManagePage = () => {
    const [activeComponent, setActiveComponent] = useState('ManageTable');

    const renderComponent = () => {
        switch (activeComponent) {
            case 'ManageTable':
                return <ManageTable />;
            case 'UnrealisedOrders':
                return <UnrealisedOrders />;
            case 'Orders':
                return <Orders />
            default:
                return <ManageTable />;
        }
    };

    return (
        <div>
            <div>
                <NavigationBar />
                <div className='container mt-4'>
                    <label>Switch table: </label>
                    <select
                        value={activeComponent}
                        onChange={(e) => setActiveComponent(e.target.value)}
                    >
                        <option value='ManageTable'>Edit products</option>
                        <option value='UnrealisedOrders'>Uncompleted Orders</option>
                        <option value='Orders'>Orders by status</option>
                    </select>
                </div>
            </div>
            <div className='mt-5'>{renderComponent()}</div>
        </div>
    );
};

export default ManagePage;
