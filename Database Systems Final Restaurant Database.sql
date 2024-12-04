CREATE DATABASE restaurantdb;
USE restaurantdb;

-- CUSTOMERS
CREATE TABLE Customers(
customer_id INT AUTO_INCREMENT,
customer_name varchar(255),
customer_dob varchar(255),
customer_address varchar(255),
customer_username varchar(255),
customer_password varchar(255),
PRIMARY KEY (customer_id)
);

INSERT INTO Customers
VALUE (2, "Testing", "01/01/01", "Testing", "Test", "Test");

INSERT INTO Customers
VALUES (1, 'Jane Doe', '05/15/1990', '123 Main St, Anytown, USA', 'janedoe', 'password123');


SELECT * FROM Customers;


CREATE TABLE Employees(
employee_id INT auto_increment,
employee_name varchar(255),
employee_username varchar(255),
employee_password varchar(255),
PRIMARY KEY (employee_id)
);

SELECT * FROM Employees;

CREATE TABLE Reservations (
    reservation_id INT AUTO_INCREMENT,
    customer_id INT,
    reservation_date VARCHAR(255),
    reservation_time TIME,
    party_size INT,
    reservation_status VARCHAR(255),
    PRIMARY KEY (reservation_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

SELECT * FROM Reservations;

CREATE TABLE Menu (
	item_id INT AUTO_INCREMENT,
    item_name VARCHAR (255),
    item_price DOUBLE,
    PRIMARY KEY (item_id)
);

INSERT INTO restaurantdb.Menu
VALUE (1, 'California Roll', 10.00);

INSERT INTO restaurantdb.Menu (item_name, item_price)
VALUE ('Spicy Tuna Roll', 12.00);

INSERT INTO restaurantdb.Menu (item_name, item_price)
VALUE ('Salmon Sashimi', 15.00);

SELECT * FROM Menu;

CREATE TABLE PaymentInfo (
	payment_info_id INT AUTO_INCREMENT,
    customer_id INT,
    card_type VARCHAR(255),
    card_number VARCHAR(255),
	expiration_month VARCHAR(255),
    expiration_year VARCHAR(255), 
    first_name VARCHAR(255), 
    last_name VARCHAR(255), 
    cvv VARCHAR(255), 
    billing_address VARCHAR(255), 
    city VARCHAR(255), 
    state VARCHAR(255), 
    zip_code INT,
    PRIMARY KEY (payment_info_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

SELECT * FROM PaymentInfo;

CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT,
    customer_id INT,
    order_date VARCHAR(255),
    total_amount DECIMAL(10, 2),
    order_status VARCHAR(255),
    PRIMARY KEY (order_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

CREATE TABLE OrderItems (
    order_item_id INT AUTO_INCREMENT,
    order_id INT,
    item_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    PRIMARY KEY (order_item_id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (item_id) REFERENCES Menu(item_id)
);

SELECT * FROM Orders;
SELECT * FROM OrderItems;

ALTER TABLE Orders MODIFY order_date DATE;


CREATE TABLE Reviews (
    review_id INT AUTO_INCREMENT,
    customer_id INT,
    review_rating INT,
    review_title VARCHAR(255),
    review_description VARCHAR (1000),
    review_date VARCHAR(255),
    review_response VARCHAR(1000),
    PRIMARY KEY (review_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

SELECT * FROM Reviews;


CREATE TABLE GiftCards (
    gift_card_id INT AUTO_INCREMENT,
    card_code VARCHAR(255) UNIQUE,
    customer_id INT,
    amount DECIMAL(10, 2),
    status VARCHAR(255),
    purchased_date VARCHAR(255),
    recipient_username VARCHAR(255),
    PRIMARY KEY (gift_card_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

SELECT * FROM GiftCards;

SELECT SUM(total_amount) AS total_money 
FROM Orders WHERE DATE(order_date) = ?;

SELECT AVG(total_amount) AS average_money 
FROM Orders WHERE DATE(order_date) = ?;

SELECT COUNT(*) AS total_reservations 
FROM Reservations WHERE reservation_date = ?;