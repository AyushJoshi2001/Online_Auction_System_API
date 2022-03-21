-- the first script for migration
create table Article (
    id int,
    content varchar(5000),
    postedAt timestamp,
    title varchar(255),
    primary key (id)
);

create table Article_authorIds (
    Article_id int not null,
    authorIds int
);
    
alter table Article_authorIds 
add constraint FK_f9ivk719aqb0rqd8my08loev7 
foreign key (Article_id) 
references Article;


create table user_table(
	uid int primary key,
    name varchar(50),
  	email varchar(50) unique not null,
   	password varchar(50) not null,
    profile_pic varchar(50), 
    mobile varchar(10),
    address varchar(50),
    userType varchar(10),
    isAdmin int not null
);

create table Product(
	pid int primary key,
	title varchar(50),
	product_pic varchar(100),
	description varchar(200),
	bid_status varchar(10),
	created_at Date,
	uid int,
	sold_status varchar(10),
	sold_to int
);

create table bid(
	b_id int primary key,
	uid int,
	pid int,
	amount int
);


create table feedback(
	f_id int primary key,
	uid int,
	message varchar(200)
);

CREATE SEQUENCE hibernate_sequence START 1;

