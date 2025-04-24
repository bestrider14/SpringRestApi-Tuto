CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE carts (
    id UUID DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    date_created DATE DEFAULT CURRENT_DATE NOT NULL
);

CREATE TABLE cart_item (
   id SERIAL NOT NULL PRIMARY KEY,
   cart_id UUID NOT NULL
       CONSTRAINT cart_item_cart_id_fk
           REFERENCES carts(id)
           ON DELETE CASCADE,

   product_id BIGINT NOT NULL
       CONSTRAINT cart_item_products_id_fk
           REFERENCES products(id)
           ON DELETE CASCADE,

   quantity INTEGER DEFAULT 1 NOT NULL,

   CONSTRAINT cart_item_cart_product_unique UNIQUE (cart_id, product_id)
);