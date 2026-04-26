package org.example.domain;

public class RecipeImageData {

    //Fields
    private final byte[] fBytes;
    private final String fFileName;
    private final String fContentType;
    private final int fFileSize;

    //Constructors
    public RecipeImageData(byte[] bytes, String fileName, String contentType, int fileSize) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("fileName is required");
        }
        if (contentType == null || contentType.isBlank()) {
            throw new IllegalArgumentException("contentType is required");
        }
        if (fileSize < 0) {
            throw new IllegalArgumentException("fileSize must be >= 0");
        }

        fBytes = bytes == null ? null : bytes.clone();
        fFileName = fileName.trim();
        fContentType = contentType.trim();
        fFileSize = fileSize;
    }

    //Public API
    public byte[] getBytes() {
        return fBytes == null ? null : fBytes.clone();
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