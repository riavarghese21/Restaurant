use restaurantdb;

create table Customers(
customer_username varchar(255),
customer_password varchar(255),
customer_name varchar(255),
customer_address varchar(255),
primary key (customer_username)
);

create Table Employees(
employee_username varchar(255),
employee_password varchar(255),
employee_name varchar(255),
primary key (employee_username)
);

create table PaymentInfo(
card_number varchar(255),
card_CVV varchar(255),
card_expiration varchar(255),
customer_username varchar(255),
primary key (card_number),
foreign key (customer_username) references Customers(customer_username)
);

create Table Menu(
item_id int,
item_name varchar(255),
item_price double,
primary key (item_id)
);
create Table Reviews(
review_id int,
review_title varchar(255),
review_contents varchar(255),
review_rating int,
primary key (review_id)
);
create Table Reservations(
reservation_id int,
reservation_time varchar(255),
reservation_date varchar (255),
primary key (reservation_id)
);
create Table Orders(
order_id int,
item_id int,
order_status varchar(255),
primary key (order_id),
foreign key (item_id) references Menu(item_id)
);


delete from PaymentInfo where customer_username = "jdoe";
delete from Customers where customer_username = "jdoe";
drop user 'jdoe'@'localhost';

select user, host from mysql.user;


insert into Menu values(1, "Maki Roll", 14.95);
insert into Reviews values(1, "Amazing", "This restaurant is awesome.", 5);
insert into Reviews values(2, "This sucks", "This restaurant is terrible.", 1);
insert into Reservations values(2, "6:00 PM", "12/22/24");
insert into Orders values(2, 1, "Ready to be picked up");

select *
from Menu;
select *
from Reviews;
select *
from Reservations;
select *
from Orders;




select *
from customers;

select *
from employees;

select *
from PaymentInfo