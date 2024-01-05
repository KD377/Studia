const express = require('express');
const app = express();
const port = 3000;

app.use(express.json());


app.use(require('./routes/products'));
app.use(require('./routes/categories'));
app.use(require('./routes/orders'));
app.use(require('./routes/status'));

app.listen(port, () => {
  console.log(`Serwer działa na http://localhost:${port}`);
});
