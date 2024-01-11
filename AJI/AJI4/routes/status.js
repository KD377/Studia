const express = require('express');
const router = express.Router();
const db = require('../database');


router.get('/status', async (req, res) => {
  try {
    const status = await db.select().from('Order_status');
    res.json(status);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Server error' });
  }
});

module.exports = router;
