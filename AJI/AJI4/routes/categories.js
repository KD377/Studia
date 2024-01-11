const express = require('express');
const router = express.Router();
const db = require('../database');


router.get('/categories', async (req, res) => {
  try {
    const categories = await db.select().from('Category');
    res.json(categories);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Server error' });
  }
});

module.exports = router;
