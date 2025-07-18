CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount NUMERIC(19,2) NOT NULL,
    discount NUMERIC(19,2) NOT NULL,
    final_amount NUMERIC(19,2) NOT NULL,
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
CREATE INDEX idx_orders_user_id ON orders(user_id);