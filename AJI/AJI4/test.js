const db = require('./database');
const jsonPatch = require('fast-json-patch');

const runTest = async () => {
    try {
        const orderId = 1;
        const order = await db('Order').where('id', orderId).first();
        console.log(JSON.parse(JSON.stringify(order)));

        const patchOperations = [
            { op: 'replace', path: '/order_status_id', value: 4 }
        ];

        try {
            jsonPatch.applyPatch(order, patchOperations);
            console.log('success');
        } catch (patchError) {
            console.log('Error applying JSON Patch:', patchError);
            console.log('denied');
        }
    } catch (error) {
        console.error(error);
    }
};

// Run the test
runTest();
