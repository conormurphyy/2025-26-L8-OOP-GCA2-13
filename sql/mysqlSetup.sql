-- Table Creation
-- Recipe
CREATE TABLE recipe (
    recipe_id INT PRIMARY KEY,
    user_id INT,
    recipe_name VARCHAR(100),
    category_id INT,
    description VARCHAR(250),
    total_calories DOUBLE,
    is_public BOOLEAN,
    recipe_image MEDIUMBLOB,
    image_file_name VARCHAR (100),
    image_content_type VARCHAR (100),
    image_size INT
);


CREATE TABLE ingredient (
    ingredient_id INT PRIMARY KEY,
    ingredient_name VARCHAR(100),
    ingredient_calories INT,
    ingredient_protein INT,
    ingredient_carbs INT,
    ingredient_fats INT
);

CREATE TABLE users (
    id INT PRIMARY KEY,
    username VARCHAR(100),
    userType ENUM('Admin', 'User'),
    userRating FLOAT
);

-- Insertion of data
INSERT INTO recipe (recipe_id, user_id, recipe_name, category_id, description, total_calories, is_public)
VALUES
    (1, 1, 'Chicken Stir Fry', 1, 'Delicious Chicken, Broccoli, and Rice Stir Fry', 650, TRUE),
    (2, 2, 'French Toast', 2, 'Yummy French Toast with Honey', 700, TRUE),
    (3, 3, 'Bulk Shake', 3, 'High Calorie Bulking Shake', 1000, TRUE),
    (4, 4, 'Chicken Curry', 4, 'Chicken curry with rice', 800, FALSE),
    (5, 5, 'Skyr Bowl', 5, 'Skyr with fruit, oats and honey', 600, TRUE),
    (6, 6, 'High Protein Breakfast', 6, 'Eggs, bagels, bacon and butter', 900, TRUE),
    (7, 7, 'Low Calorie Breakfast', 7, 'Fruit Bowl', 300, FALSE),
    (8, 8, 'Mince Bolanaise', 8, 'High Protein Mince Bolanaise', 900, TRUE),
    (9, 9, 'Taco Bowl', 9, 'High Calorie Taco Bowl with mince, rice and avocado', 950, FALSE),
    (10, 10, 'Pre Workout Overnight Oats', 10, 'High Carbs Overnight Oats', 600, TRUE);

--ALL ingredients are by 100g
INSERT INTO ingredient (ingredient_id, ingredient_name, ingredient_calories, ingredient_protein, ingredient_carbs, ingredient_fats)
VALUES
    (1, 'Chicken Breast', 165, 31, 0, 3),
    (2, 'White Rice', 130, 2, 28, 0),
    (3, 'Broccoli', 55, 4, 11, 1),
    (4, 'Eggs', 155, 13, 1, 11),
    (5, 'Milk', 60, 3, 5, 3),
    (6, 'Oats', 389, 17, 66, 7),
    (7, 'Banana', 89, 1, 23, 0),
    (8, 'Beef Mince', 250, 26, 0, 15),
    (9, 'Avocado', 160, 2, 9, 15),
    (10, 'Honey', 304, 0, 82, 0),
    (11, 'Bagel', 250, 9, 48, 1),
    (12, 'Bacon', 541, 37, 1, 42),
    (13, 'Butter', 717, 1, 0, 81),
    (14, 'Skyr', 60, 11, 4, 0),
    (15, 'Strawberries', 32, 1, 8, 0);

INSERT INTO users (id, username, userType, userRating)
VALUES
    (1,'MikeCooks123', 'User', 4.3),
    (2,'Jason123', 'User', 3.3),
    (3,'Richie111', 'User', 2.1),
    (4,'Carla114', 'User', 1.7),
    (5,'Rebecca1234', 'Admin', 5.0),
    (6,'Becca123', 'User', 4.5),
    (7,'LordCook', 'User', 3.3),
    (8,'CaptainCook', 'User', 2.3),
    (9,'JessiePinkman1', 'User', 4.6),
    (10,'WillyWonka', 'Admin', 4.1);

