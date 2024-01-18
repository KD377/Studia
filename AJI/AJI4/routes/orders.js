const express = require('express');
const router = express.Router();
const db = require('../database');
const { StatusCodes, getReasonPhrase } = require('http-status-codes');
const jsonPatch = require('fast-json-patch');


router.get('/orders', async (req, res) => {
    try {
        const orders = await db
            .select(
                'Order.id as order_id',
                'Order.confirm_date',
                'Order.order_status_id',
                'Order.username',
                'Order.email',
                'Order.phone_number',
                'Product_order.product_id',
                'Product_order.quantity'
            )
            .from('Order')
            .leftJoin('Product_order', 'Order.id', 'Product_order.order_id')
            .orderBy('Order.id');

        const groupedOrders = orders.reduce((acc, order) => {
            const existingOrder = acc.find((o) => o.order_id === order.order_id);

            if (existingOrder) {
                existingOrder.products.push(order.product_id);
                existingOrder.quantities.push(order.quantity);
            } else {
                const newOrder = {
                    order_id: order.order_id,
                    confirm_date: order.confirm_date,
                    order_status_id: order.order_status_id,
                    username: order.username,
                    email: order.email,
                    phone_number: order.phone_number,
                    products: [order.product_id],
                    quantities: [order.quantity],
                };
                acc.push(newOrder);
            }

            return acc;
        }, []);

        res.status(StatusCodes.OK).send(groupedOrders);
    } catch (error) {
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
            error: getReasonPhrase(StatusCodes.INTERNAL_SERVER_ERROR)
        });;
    }
});



router.post('/orders', async (req, res) => {
    try {
        const { confirm_date, order_status, username, email, phone_number, products, quantities } = req.body;

        // Sprawdzenie, czy wszystkie wymagane pola są dostępne
        if (!confirm_date || !order_status || !username || !email || !phone_number || !products || !quantities) {
            return res.status(StatusCodes.BAD_REQUEST).send({ error: 'Not all required parameters have been passed' });
        }

        // Sprawdzenie, czy pola dotyczące użytkownika nie są puste
        if (username.trim() === '' || email.trim() === '' || phone_number.trim() === '') {
            return res.status(StatusCodes.BAD_REQUEST).send({ error: 'User data must not be empty' });
        }

        // Sprawdzenie, czy numer telefonu nie zawiera liter
        if (!/^\d+$/.test(phone_number)) {
            return res.status(StatusCodes.BAD_REQUEST).send({ error: 'Phone number must contain number only' });
        }

        // Sprawdzenie, czy istnieje status zamówienia o podanej nazwie
        const orderStatus = await db('Order_status').where('name', order_status).first();
        if (!orderStatus) {
            return res.status(StatusCodes.BAD_REQUEST).send({ error: 'Wrong order status' });
        }

        // Sprawdzenie, czy istnieją produkty o podanych nazwach
        const productIds = await db('Product').whereIn('name', products).pluck('id');

        if (productIds.length !== products.length) {
            return res.status(StatusCodes.BAD_REQUEST).send({ error: 'Wrong product ids' });
        }

        // Sprawdzenie, czy ilości zamówionych produktów są dodatnimi liczbami
        if (quantities.some(qty => qty <= 0 || !Number.isInteger(qty))) {
            return res.status(StatusCodes.BAD_REQUEST).send({ error: 'All qunatities must be positive ' });
        }

        const [orderId] = await db('Order').insert({
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

        res.status(StatusCodes.OK).json({ success: true, order_id: orderId, pieces: productIds.length, status: order_status, username: username, email: email, phone: phone_number });
    } catch (error) {
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({error: getReasonPhrase(StatusCodes.INTERNAL_SERVER_ERROR)});
    }
});

// router.patch('/orders/:id', async (req, res) => {
//     try {
//         const orderId = req.params.id;
//         const { order_status_id } = req.body;

//         // Sprawdzenie, czy order_status_id został przekazany
//         if (!order_status_id) {
//             return res.status(StatusCodes.BAD_REQUEST).send({ error: 'New order status has not been passed' });
//         }

//         // Pobranie zamówienia o podanym identyfikatorze
//         const order = await db('Order').where('id', orderId).first();

//         // Sprawdzenie, czy zamówienie istnieje
//         if (!order) {
//             return res.status(StatusCodes.NOT_FOUND).send({ error: 'Order does not exist' });
//         }


//         const currentStatus = await db('Order_status').where('id', order.order_status_id).first();


//         const newStatus = await db('Order_status').where('id', order_status_id).first();

//         // Sprawdzenie, czy nowy status jest wyższy lub równy niż aktualny status
//         if (currentStatus.id >= newStatus.id) {
//             return res.status(StatusCodes.BAD_REQUEST).send({ error: 'Cannot change order status backwards' });
//         }

//         // Jeśli status to "CANCELLED", nie można go już zmieniać
//         if (currentStatus.name === 'CANCELLED') {
//             return res.status(StatusCodes.BAD_REQUEST).send({ error: 'Order has been already cancelled and that state cannot be changed' });
//         }

//         // Aktualizacja statusu zamówienia
//         await db('Order').where('id', orderId).update({ order_status_id });

//         res.send({ success: true });
//     } catch (error) {
//         console.error(error);
//         res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({ error: 'Server error' });
//     }
// });

 // You may need to install this package using npm install fast-json-patch

router.patch('/orders/:id', async (req, res) => {
    try {
        const orderId = req.params.id;
        const patchOperations = req.body;

        // Sprawdzenie, czy patchOperations zostały przekazane
        if (!Array.isArray(patchOperations) || patchOperations.length === 0) {
            return res.status(StatusCodes.BAD_REQUEST).send({ error: 'Patch operations are missing or empty' });
        }

        // Pobranie zamówienia o podanym identyfikatorze
        const order = await db('Order').where('id', orderId).first();
        const prevId = order.order_status_id;
        console.log(order);
        if(orderId < prevId) {
            return res.status(StatusCodes.BAD_REQUEST).send({error: 'Cannot change the status backwards'});
        }

        // Sprawdzenie, czy zamówienie istnieje
        if (!order) {
            return res.status(StatusCodes.NOT_FOUND).send({ error: 'Order does not exist' });
        }

        // Konwersja zamówienia na JSON dla operacji JSON Patch
        const orderJson = JSON.parse(JSON.stringify(order));

        try{
            jsonPatch.applyPatch(orderJson, patchOperations);
        } catch(patchError){
            return res.status(StatusCodes.BAD_REQUEST).send({ error: patchError });
        }
        const date = new Date(orderJson.confirm_date);
        orderJson.confirm_date = date;

        // Aktualizacja zamówienia na podstawie zmodyfikowanego orderJson
        await db('Order').where('id', orderId).update(orderJson);

        res.send({ success: true });
    } catch (error) {
        console.error(error);
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({ error: 'Server error' });
    }
});



router.get('/orders/status/:id', async (req, res) => {
    try {
        const statusId = req.params.id;

        const orders = await db
            .select(
                'Order.id as order_id',
                'Order.confirm_date',
                'Order.order_status_id',
                'Order.username',
                'Order.email',
                'Order.phone_number',
                'Product_order.product_id',
                'Product_order.quantity'
            )
            .from('Order')
            .leftJoin('Product_order', 'Order.id', 'Product_order.order_id')
            .where('Order.order_status_id', statusId)
            .orderBy('Order.id');

        const groupedOrders = orders.reduce((acc, order) => {
            const existingOrder = acc.find((o) => o.order_id === order.order_id);

            if (existingOrder) {
                existingOrder.products.push(order.product_id);
                existingOrder.quantities.push(order.quantity);
            } else {
                const newOrder = {
                    order_id: order.order_id,
                    confirm_date: order.confirm_date,
                    order_status_id: order.order_status_id,
                    username: order.username,
                    email: order.email,
                    phone_number: order.phone_number,
                    products: [order.product_id],
                    quantities: [order.quantity],
                };
                acc.push(newOrder);
            }

            return acc;
        }, []);

        res.json(groupedOrders);
    } catch (error) {
        console.error(error);
        res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({ error: getReasonPhrase(StatusCodes.INTERNAL_SERVER_ERROR) });
    }
});


module.exports = router;
