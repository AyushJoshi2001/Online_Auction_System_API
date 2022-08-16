-- the first script for migration

create table user_table(
	uid int primary key,
    name varchar(200),
  	email varchar(200) unique not null,
   	password varchar(50) not null,
    profile_pic varchar(500), 
    mobile varchar(10),
    address varchar(500),
    about varchar(500),
    isAdmin int not null
);

create table Product(
	pid int primary key,
	title varchar(500),
	product_pic varchar(500),
	description varchar(500),
	base_price int, 
	bid_status varchar(10),
	created_at timestamp with time zone,
	uid int,
	sold_status varchar(10),
	sold_to int,
	sold_price int,
	bid_end_date timestamp with time zone
);

create table bid(
	b_id int primary key,
	uid int,
	pid int,
	amount int,
	product_name varchar(200)
);


create table feedback(
	f_id int primary key,
	uid int,
	message varchar(200)
);

CREATE SEQUENCE hibernate_sequence START 1;

