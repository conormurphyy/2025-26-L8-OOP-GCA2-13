package org.example.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileUploadPayloadTest {

    @Test
    void constructorAndGetters() {
        FileUploadPayload p = new FileUploadPayload(42, "file.txt", "text/plain", 1234, "data");
        assertEquals(42, p.getEntityId());
        assertEquals("file.txt", p.getFileName());
        assertEquals("text/plain", p.getContentType());
        assertEquals(1234, p.getFileSize());
        assertEquals("data", p.getFileData());
    }

    @Test
    void defaultConstructorHasDefaults() {
        FileUploadPayload p = new FileUploadPayload();
        assertEquals(0, p.getEntityId());
        assertEquals("", p.getFileName());
        assertEquals("", p.getContentType());
        assertEquals(0, p.getFileSize());
        assertEquals("", p.getFileData());
    }

    @Test
    void settersUpdateValues() {
        FileUploadPayload p = new FileUploadPayload();
        p.setEntityId(99);
        p.setFileName("new.txt");
        p.setContentType("text/plain");
        p.setFileSize(555);
        p.setFileData("hello");

        assertEquals(99, p.getEntityId());
        assertEquals("new.txt", p.getFileName());
        assertEquals("text/plain", p.getContentType());
        assertEquals(555, p.getFileSize());
        assertEquals("hello", p.getFileData());
    }
}