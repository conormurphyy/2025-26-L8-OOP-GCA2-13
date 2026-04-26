package org.example.dao;

import org.example.dao.jdbc.JDBCRecipeDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class jdbcRecipeDao {

    @Test
    void getAllRecipes_returnsList() throws Exception {
        JDBCRecipeDao dao = new JDBCRecipeDao(
                "jdbc:mysql://localhost:3306/recipehub",
                "root",
                ""
        );

        assertNotNull(dao.getAllRecipes()); // basic read test
    }
}