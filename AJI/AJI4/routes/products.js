const express = require('express');
const router = express.Router();
const db = require('../database');


router.get('/products', async (req, res) => {
  try {
    const products = await db.select().from('Product');
    res.json(products);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Server error' });
  }
});

router.get('/products/:id', async (req, res) => {
  const productId = req.params.id;
  try {
    const product = await db('Product').where('id', productId).first();
    if (product) {
      res.json(product);
    } else {
      res.status(404).json({ error: 'Product not found' });
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Server error' });
  }
});

router.post('/products', async (req, res) => {
    const { name, description, price, weight, category } = req.body;
  
    if (!name || !description || !price|| !weight || !category) {
      return res.status(400).json({ error: 'Arguments missing' });
    }
  
    try {
      const existingCategory = await db('Category').where('name', category).first();
  
      if (!existingCategory) {
        return res.status(400).json({ error: 'No such category' });
      }
  
      const newProduct = {
        name,
        description,
        price,
        weight,
        category_id: existingCategory.id,
      };
  
      
      const insertedProduct = await db('Product').insert(newProduct);
  
      res.json({ id: insertedProduct[0] });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Server error' });
    }
  });
  

  router.put('/products/:id', async (req, res) => {
    const productId = req.params.id;
    const { name, description, price, weight, category } = req.body;
  
    if (!name || !description || !price || !weight || !category) {
      return res.status(400).json({ error: 'Brak wymaganych parametrów' });
    }
  
    try {
      const existingCategory = await db('Category').where('name', category).first();
  
      if (!existingCategory) {
        return res.status(400).json({ error: 'No such category' });
      }

      const updatedProduct = {
        name,
        description,
        price,
        weight,
        category_id: existingCategory.id,
      };

      const result = await db('Product').where('id', productId).update(updatedProduct);
  
      if (result) {
        res.json({ success: true });
      } else {
        res.status(404).json({ error: 'Produkt nie znaleziony' });
      }
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Błąd serwera' });
    }
  });
  

module.exports = router;
