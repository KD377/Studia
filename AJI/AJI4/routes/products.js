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

  // Sprawdzenie, czy wszystkie wymagane pola są dostępne
  if (!name || !description || !price || !weight || !category) {
      return res.status(400).json({ error: 'Not all parameteres have been passed' });
  }

  // Sprawdzenie, czy cena i waga są dodatnimi liczbami
  if (price < 0 || weight < 0) {
      return res.status(400).json({ error: 'Price and weight must be positive' });
  }

  // Sprawdzenie, czy opis i nazwa nie są puste
  if (description.trim() === '' || name.trim() === '') {
      return res.status(400).json({ error: 'Name and description cannot be empty' });
  }

  try {
      // Sprawdzenie, czy istnieje kategoria o podanej nazwie
      const existingCategory = await db('Category').where('name', category).first();

      if (!existingCategory) {
          return res.status(400).json({ error: 'Given category does not exist' });
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

  // Sprawdzenie, czy wszystkie wymagane pola są dostępne
  if (!name || !description || !price || !weight || !category) {
      return res.status(400).json({ error: 'Not all parameters have been passed' });
  }

  // Sprawdzenie, czy cena i waga są dodatnimi liczbami
  if (price < 0 || weight < 0) {
      return res.status(400).json({ error: 'Price and weight must be positivie' });
  }

  // Sprawdzenie, czy opis i nazwa nie są puste
  if (description.trim() === '' || name.trim() === '') {
      return res.status(400).json({ error: 'Name and description cannot be empty' });
  }

  try {
      // Sprawdzenie, czy istnieje kategoria o podanej nazwie
      const existingCategory = await db('Category').where('name', category).first();

      if (!existingCategory) {
          return res.status(400).json({ error: 'No such category' });
      }

      // Sprawdzenie, czy istnieje produkt o podanym ID
      const existingProduct = await db('Product').where('id', productId).first();

      if (!existingProduct) {
          return res.status(404).json({ error: 'No product of this id' });
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
          res.status(500).json({ error: 'Error when trying to update database' });
      }
  } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Server error' });
  }
});

  

module.exports = router;
