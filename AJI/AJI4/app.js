const express = require('express');
const cors = require('cors'); 
const app = express();
const port = 4000;

const allowedOrigins = ['http://localhost:3000']; 

const corsOptions = {
  origin: function (origin, callback) {
    if (allowedOrigins.indexOf(origin) !== -1 || !origin) {
      callback(null, true);
    } else {
      callback(new Error('Not allowed by CORS'));
    }
  },
};

app.use(cors(corsOptions));
app.use(express.json());

app.use(require('./routes/products'));
app.use(require('./routes/categories'));
app.use(require('./routes/orders'));
app.use(require('./routes/status'));

app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
