# G-CA2-Recipe_and_Macro_Tracker

Db name - recipehub


USER QUERIES

Creation of table -
CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    userType ENUM('Admin','User') NOT NULL DEFAULT 'User',
    userRating DOUBLE NOT NULL DEFAULT 0,
	PRIMARY KEY (id)
    );
