const express = require('express');
const router = express.Router();
const db = require('../database');


router.get('/orders', async (req, res) => {
    try {
        const orders = await db('Order');

        res.json(orders);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Błąd serwera' });
    }
});

router.post('/orders', async (req, res) => {
    try {
        const { confirm_date, order_status, username, email, phone_number, products, quantities } = req.body;

        if (!confirm_date || !order_status || !username || !email || !phone_number || !products || !quantities) {
            return res.status(400).json({ error: 'Brak wymaganych parametrów' });
        }

        if (products.length !== quantities.length) {
            return res.status(400).json({ error: 'Nieprawidłowa ilość produktów lub ilości zamówionych produktów' });
        }

        const orderStatus = await db('Order_status').where('name', order_status).first();
        if (!orderStatus) {
            return res.status(400).json({ error: 'Nieprawidłowy status zamówienia' });
        }

        const productIds = await db('Product').whereIn('name', products).pluck('id');

        const [orderId] = await db('Order').returning('id').insert({
            confirm_date,
            order_status_id: orderStatus.id,
            username,
            email,
            phone_number,
        });

        for (let i = 0; i < products.length; i++) {
            await db('Product_Order').insert({
                product_id: productIds[i],
                order_id: orderId,
                quantity: quantities[i],
            });
        }

        res.json({ success: true });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Błąd serwera' });
    }
});

router.patch('/orders/:id', async (req, res) => {
    try {
      const orderId = req.params.id;
      const { order_status_id } = req.body;
  
      if (!order_status_id) {
        return res.status(400).json({ error: 'Brak wymaganego parametru order_status_id' });
      }
  
      const order = await db('Order').where('id', orderId).first();
  
      if (!order) {
        return res.status(404).json({ error: 'Zamówienie nie znalezione' });
      }
  
      await db('Order').where('id', orderId).update({ order_status_id });
  
      res.json({ success: true });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Błąd serwera' });
    }
  });

router.get('/orders/status/:id', async (req, res) => {
    try {
        const statusId = req.params.id;

        const orders = await db('Order').where('order_status_id', statusId);

        res.json({ orders });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Błąd serwera' });
    }
});


module.exports = router;
