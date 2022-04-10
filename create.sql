-- DROP DATABASE IF EXISTS tienda;
CREATE DATABASE tienda with encoding "UTF8";

\c tienda;

CREATE TABLE product (
	id SERIAL PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	price real NOT NULL,
	stock integer NOT NULL,
	date_start date NOT NULL,
	date_end date NOT NULL,
	CONSTRAINT chk_price CHECK(price>0),
	CONSTRAINT chk_stock CHECK(stock>0),
	CONSTRAINT chk_catalog CHECK(date_end>=date_start)
);

CREATE TABLE pack (
	id SERIAL PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	price real NOT NULL,
	stock integer NOT NULL,
	date_start date NOT NULL,
	date_end date NOT NULL,
	discount real NOT NULL,
	CONSTRAINT chk_price_pack CHECK(price>0),
	-- CONSTRAINT chk_stock_pack CHECK(stock>0),
	CONSTRAINT chk_catalog_pack CHECK(date_end>=date_start),
	CONSTRAINT chk_discount CHECK(discount>=0 and discount<=100)
);

CREATE TABLE pack_products (
	id_pack INTEGER,
	id_producto INTEGER,
	PRIMARY KEY(id_pack, id_producto),
	CONSTRAINT fk_id_pack FOREIGN KEY(id_pack) references pack(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_id_producto FOREIGN KEY(id_producto) references product(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE client (
	id SERIAL PRIMARY KEY,
	dni VARCHAR(10) NOT NULL,
	name VARCHAR(15) NOT NULL,
	lastname VARCHAR(20) NOT NULL,
	birthdate DATE NULL,
	email VARCHAR(25) NULL,
	address VARCHAR(50) array NOT NULL,
	phone VARCHAR(9) array NOT NULL
);

CREATE TABLE provider (
	id SERIAL PRIMARY KEY,
	dni VARCHAR(10) NOT NULL,
	name VARCHAR(15) NOT NULL,
	lastname VARCHAR(20) NOT NULL,
	birthdate DATE NULL,
	email VARCHAR(25) NULL,
	address VARCHAR(50) array NOT NULL,
	phone VARCHAR(9) array NOT NULL
);

CREATE TABLE presence (
	id int,
	date_enter timestamp,
	date_leave timestamp,
	PRIMARY KEY(id, date_enter)
);