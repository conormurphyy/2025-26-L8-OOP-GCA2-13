package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class RecipeImageDataTest {

    @Test
    public void getBytes_returnsDefensiveCopy() {
        byte[] original = new byte[] {1, 2, 3};
        RecipeImageData data = new RecipeImageData(original, "photo.png", "image/png", 3);

        byte[] firstRead = data.getBytes();
        byte[] secondRead = data.getBytes();

        assertNotSame(original, firstRead);
        assertNotSame(firstRead, secondRead);
        assertArrayEquals(new byte[] {1, 2, 3}, secondRead);
    }
}