import React from 'react';
import NavigationBar from '../components/NavigationBar';
import EditProduct from '../components/EditProduct';
import { useLocation} from 'react-router-dom';

const EditProductPage = () => {
  const location = useLocation();
  const product = location.state.product;

  return (
    <div>
        <div>
         <NavigationBar />
        </div>
        <div className='mt-5'>
            <EditProduct product = {product}/>
        </div>
    </div>
  );
};

export default EditProductPage;
