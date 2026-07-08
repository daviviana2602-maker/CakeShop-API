ALTER TABLE orders
    ADD CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id)
            REFERENCES users(id);

ALTER TABLE cart_items
    ADD CONSTRAINT fk_cart_order
        FOREIGN KEY (order_id)
            REFERENCES orders(id);