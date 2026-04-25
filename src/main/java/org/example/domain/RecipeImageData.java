package org.example.domain;

public class RecipeImageData {
    private final byte[] fBytes;
    private final String fFileName;
    private final String fContentType;
    private final int fFileSize;

    public RecipeImageData(byte[] bytes, String fileName, String contentType, int fileSize) {
        this.fBytes = bytes;
        this.fFileName = fileName;
        this.fContentType = contentType;
        this.fFileSize = fileSize;
    }

    public byte[] getBytes() {
        return fBytes;
    }

    public String getFileName() {
        return fFileName;
    }

    public String getContentType() {
        return fContentType;
    }

    public int getFileSize() {
        return fFileSize;
    }
}