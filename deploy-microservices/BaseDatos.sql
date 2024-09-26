DROP DATABASE IF EXISTS db_assessment_sofka;
CREATE DATABASE db_assessment_sofka;



use db_assessment_sofka;

-- generate tables

DROP TABLE IF EXISTS people;
CREATE TABLE people(
    person_id 		int primary key auto_increment,
    full_name 		varchar(250)   not null,
    gender   		varchar(250)   not null,
    age   		    int            not null,
    identification  varchar(15)    not null,
    address         text,
    phone 			varchar(15),
    created_at      timestamp,
    status_record   varchar(10) not null,
    removed_at      timestamp,
    unique(identification)
);


DROP TABLE IF EXISTS customers;
CREATE TABLE customers(
	
    customer_id   int primary key,
    password      varchar(100),
    status        varchar(10),
	CONSTRAINT rel_customers_persona FOREIGN KEY(customer_id) REFERENCES people(person_id)
);



DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts(
	
    account_id   		int primary key  auto_increment,
    account_number      varchar(100) not null,
    account_type        varchar(20)  not null,
    balance				decimal(6,2) not null,
    customer_id         int not null,
	status_record       varchar(10) not null,
	created_at          timestamp,
    removed_at			timestamp,
	CONSTRAINT rel_account_customer FOREIGN KEY(customer_id) REFERENCES customers(customer_id)
);


DROP TABLE IF EXISTS movimientos;
CREATE TABLE movimientos(
	
    transaction_id      int primary key  auto_increment,
    account_id          int not null,
    value               decimal(6,2) not null,
    balance				decimal(6,2) not null,
    movement_type        varchar(20) not null,
	status_record       varchar(10) not null,
    description         varchar(100),
	created_at          timestamp,
    removed_at			timestamp,   
    available_balance	 decimal(6,2) not null,
	CONSTRAINT rel_movimientos_account FOREIGN KEY(account_id) REFERENCES accounts(account_id)
);


