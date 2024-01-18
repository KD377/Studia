// categories.js
const { StatusCodes, getReasonPhrase } = require('http-status-codes');

const express = require('express');
const router = express.Router();
const db = require('../database');


router.get('/categories', async (req, res) => {
  try {
    const categories = await db.select().from('Category');
    res.status(StatusCodes.OK).send(categories);
  } catch (error) {
    res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
      error: getReasonPhrase(StatusCodes.INTERNAL_SERVER_ERROR)
    });
  }
});

module.exports = router;
