import React from 'react';
import NavigationBar from '../components/NavigationBar';
import ProductTable from '../components/ProductTable';

const MainPage = () => {
  return (
    <div>
    <NavigationBar />
    <div className='container my-5'>
      <ProductTable />
    </div>
    
  </div>
  );
};

export default MainPage;
