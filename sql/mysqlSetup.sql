-- Table Creation
-- Recipe
CREATE TABLE recipe (
    recipe_id INT PRIMARY KEY,
    user_id INT,
    recipe_name VARCHAR(100),
    category_id INT,
    description VARCHAR(250),
    total_calories DOUBLE,
    is_public BOOLEAN
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