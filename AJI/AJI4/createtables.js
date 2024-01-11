const knex = require('knex')({
    client: 'mysql2',
    connection: {
      host: '127.0.0.1',
      user: 'root',
      password: '123456', 
      database: 'sklep',
    },
  });
  
  // Tabela Kategoria
  knex.schema.createTable('Category', (table) => {
    table.increments('id').primary();
    table.string('name');
  })
  .then(() => {
    // Tabela Produkt
    return knex.schema.createTable('Product', (table) => {
      table.increments('id').primary();
      table.string('name');
      table.text('description');
      table.decimal('price', 10, 2);
      table.decimal('weight', 10, 2);
      table.integer('category_id').unsigned().references('Category.id');
    });
  })
  .then(() => {
    // Tabela Stan Zamówienia
    return knex.schema.createTable('Order_status', (table) => {
      table.increments('id').primary();
      table.string('name');
    });
  })
  .then(() => {
    // Tabela Zamowienie
    return knex.schema.createTable('Order', (table) => {
      table.increments('id').primary();
      table.date('confirm_date');
      table.integer('order_status_id').unsigned().references('Order_status.id');
      table.string('username');
      table.string('email');
      table.string('phone_number');
    });
  })
  .then(() => {
    // Tablica produkt_zamowienie
    return knex.schema.createTable('Product_Order', (table) => {
      table.integer('product_id').unsigned().references('Product.id');
      table.integer('order_id').unsigned().references('Order.id');
      table.integer('quantity');
    });
  })
  .finally(() => {
    knex.destroy();
  });
  