const express = require('express');
const app = express();
const port = 3000;

app.use(express.json());


app.use(require('./routes/products'));
//app.use(require('./categories'));
//app.use(require('./orders'));
//app.use(require('./orderStatus'));

app.listen(port, () => {
  console.log(`Serwer działa na http://localhost:${port}`);
});
