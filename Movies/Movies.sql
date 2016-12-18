CREATE DATABASE movies;
USE movies;
CREATE TABLE categorys(
	id VARCHAR(50) primary key,
	name VARCHAR(50) NOT NULL UNIQUE,
	description VARCHAR(50)
);
CREATE TABLE books(
	id INT primary key,
	name VARCHAR(50) NOT NULL UNIQUE,
	author VARCHAR(50),
	price FLOAT(8,2),
	path VARCHAR(50),
	photoFileName VARCHAR(50),
	description VARCHAR(50),
	categoryid VARCHAR(50),
	CONSTRAINT category_id_fk FOREIGN KEY(categoryid) REFERENCES categorys(id)
);

