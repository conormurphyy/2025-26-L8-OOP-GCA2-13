
package org.example.domain;

public class FileUploadPayload {

    private int    fEntityId;
    private String fFileName;
    private String fContentType;
    private int    fFileSize;
    private String fFileData;    

    
    public FileUploadPayload() {
        fEntityId    = 0;
        fFileName    = "";
        fContentType = "";
        fFileSize    = 0;
        fFileData    = "";
    }

    public FileUploadPayload(int entityId, String fileName,String contentType, int fileSize,String fileData) {
        fEntityId    = entityId;
        fFileName    = fileName;
        fContentType = contentType;
        fFileSize    = fileSize;
        fFileData    = fileData;
    }

    
    public int getEntityId() { 
        return fEntityId; 
    }

    
    public void setEntityId(int entityId) { 
        fEntityId = entityId; 
    }

    
    public String getFileName() { 
        return fFileName; 
    }

    
    public void setFileName(String f){ 
        fFileName = f; 
    }

    
    public String getContentType() { 
        return fContentType;
    }

    
    public void setContentType(String contentType){ 
        fContentType = contentType; 
    }

    
    public int getFileSize() { 
        return fFileSize; 
    }

    
    public void setFileSize(int fileSize){ 
        fFileSize = fileSize; 
    }

    
    public String getFileData() { 
        return fFileData; 
    }

    
    public void setFileData(String fileData) {
        fFileData = fileData; 
    }
}