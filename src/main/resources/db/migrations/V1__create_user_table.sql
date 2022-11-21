CREATE TABLE users (
	user_id INT auto_increment NOT NULL,
	name varchar(100) NULL,
	user_name varchar(100) NULL,
	email varchar(100) NULL,
	phone INT NULL,
	CONSTRAINT users_PK PRIMARY KEY (user_id)
)