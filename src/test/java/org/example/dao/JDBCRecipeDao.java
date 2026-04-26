package org.example.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.jdbc.JDBCRecipeDao;
import org.example.domain.Recipe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    void insertRecipeWithImageReturnGenID() throws Exception {
        JDBCRecipeDao dao = new JDBCRecipeDao(
                "jdbc:mysql://localhost:3306/recipehub",
                "root",
                ""
        );
        byte[] img = "x".getBytes();

        Recipe r1 = new Recipe(0,1,"Test",1,"d",100,true,img,"x.txt","text/plain",1000);
        int id = dao.insertRecipeWithImage(r1);
        System.out.println(id);
        assertTrue(id>0);
        //each time it runs it creates a new recipe in the database and the id increases by 1

    }
    @Test
    void recipeJsonRoundTrip() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Recipe r = new Recipe(1,2,"Test",1,"d",100,true);
        String json = mapper.writeValueAsString(r);//serialize to json
        Recipe r2 = mapper.readValue(json, Recipe.class);//deserialize from json

        assertEquals(r,r2);
    }
}
