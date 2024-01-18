const express = require('express');
const router = express.Router();
const db = require('../database');
const { StatusCodes } = require('http-status-codes');


router.get('/status', async (req, res) => {
  try {
    const status = await db.select().from('Order_status');
    res.status(StatusCodes.OK).send(status);
  } catch (error) {
    res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({ error: 'Server error' });
  }
});

module.exports = router;
