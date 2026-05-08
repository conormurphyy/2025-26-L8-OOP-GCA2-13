package org.example.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecipeImageDataTest {

    @Test
    void nullFieldsAreHandled() {
        RecipeImageData r = new RecipeImageData(null, null, null, 0);
        assertNull(r.getBytes());
        assertNull(r.getFileName());
        assertNull(r.getContentType());
        assertEquals(0, r.getFileSize());
    }

    @Test
    void constructorAndGetters() {
        byte[] data = {1,2,3};
        RecipeImageData r = new RecipeImageData(data, "file.png", "image/png", 123);
        assertArrayEquals(data, r.getBytes());
        assertEquals("file.png", r.getFileName());
        assertEquals("image/png", r.getContentType());
        assertEquals(123, r.getFileSize());
    }

    @Test
    void sameContentsAreEqualByFieldComparison() {
        byte[] data = {1,2,3};
        RecipeImageData a = new RecipeImageData(data, "f", "t", 1);
        RecipeImageData b = new RecipeImageData(data.clone(), "f", "t", 1);

        assertArrayEquals(a.getBytes(), b.getBytes());
        assertEquals(a.getFileName(), b.getFileName());
        assertEquals(a.getContentType(), b.getContentType());
        assertEquals(a.getFileSize(), b.getFileSize());
    }

    @Test
    void clonedByteArrayStillMatchesContent() {
        byte[] original = {7,8,9};
        byte[] clone = original.clone();
        RecipeImageData a = new RecipeImageData(original, "f", "t", 3);
        RecipeImageData b = new RecipeImageData(clone, "f", "t", 3);

        assertArrayEquals(a.getBytes(), b.getBytes());
    }

    @Test
    void differentFileNameMeansDifferentFieldValue() {
        byte[] data = {1,2,3};
        RecipeImageData a = new RecipeImageData(data, "fileA", "image/png", 3);
        RecipeImageData b = new RecipeImageData(data, "fileB", "image/png", 3);

        assertNotEquals(a.getFileName(), b.getFileName());
    }

}