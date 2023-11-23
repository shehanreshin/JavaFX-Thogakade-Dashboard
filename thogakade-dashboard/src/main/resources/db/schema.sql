CREATE DATABASE ThogaKade;

USE ThogaKade;

CREATE TABLE Customer(
    id VARCHAR(6) NOT NULL,
    name VARCHAR(30),
    address VARCHAR(30),
    salary DECIMAL(10,2),
    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE Orders(
    id VARCHAR(6) NOT NULL,
    date DATE,
    customerId VARCHAR(6) NOT NULL,
    CONSTRAINT PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY(customerId) REFERENCES Customer(id) on Delete Cascade on Update Cascade
);

CREATE TABLE Item(
    code VARCHAR(6) NOT NULL,
    description VARCHAR(50),
    unitPrice DECIMAL(8,2),
    qtyOnHand INT(5),
    CONSTRAINT PRIMARY KEY (code)
);

CREATE TABLE OrderDetail(
    orderId VARCHAR(6) NOT NULL,
    itemCode VARCHAR(6) NOT NULL,
    qty INT(11),
    unitPrice DECIMAL(8,2),
    CONSTRAINT PRIMARY KEY (orderId,itemCode),
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES Orders(id) on Delete Cascade on Update Cascade,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item(code) on Delete Cascade on Update Cascade
);