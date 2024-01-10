const express = require('express');
const cors = require('cors'); 
const app = express();
const port = 4000;

app.use(cors());
app.use(express.json());

app.use(require('./routes/products'));
app.use(require('./routes/categories'));
app.use(require('./routes/orders'));
app.use(require('./routes/status'));

app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
