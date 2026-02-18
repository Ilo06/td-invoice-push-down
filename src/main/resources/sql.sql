CREATE DATABASE invoice;

\c invoice;

CREATE TYPE invoice_status AS ENUM ('DRAFT', 'CONFIRMED', 'PAID' );

CREATE TABLE invoice (
id SERIAL PRIMARY KEY,
customer_name VARCHAR NOT NULL,
status invoice_status) ;

CREATE TABLE invoice_line (
id SERIAL PRIMARY KEY,
invoice_id INT NOT NULL REFERENCES invoice (id),
label VARCHAR NOT NULL,
quantity INT NOT NULL,
unit_price NUMERIC (10,2) NOT NULL

);

INSERT INTO invoice (id,customer_name, status) VALUES
(1, 'Alice', 'CONFIRMED'),
(2, 'Bob', 'PAID'),
(3, 'Charlie', 'DRAFT');

INSERT INTO invoice_line (invoice_id, label, quantity, unit_price) VALUES
(1, 'Produit A', 2, 100),
(1, 'Produit B', 1, 50),
(2, 'Produit A', 5, 100),
(2, 'Service C', 1, 200),
(3, 'Produit B', 3, 50);



CREATE TABLE tax_config
(
    id    SERIAL PRIMARY KEY,
    label VARCHAR       NOT NULL,
    rate  NUMERIC(5, 2) NOT NULL
);

INSERT INTO tax_config (label, rate) VALUES
    ('TVA STANDARD', 20);