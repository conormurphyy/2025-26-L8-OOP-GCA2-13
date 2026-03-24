package org.example.shared;

public class ServerResponse<T> {

    //Fields
    private String fStatus;
    private String fMessage;
    private T fData;

    // Constructors
    public ServerResponse() {
        fStatus = "";
        fMessage = "";
        fData = null;
    }

    public ServerResponse(String status, String message, T data) {
        fStatus = status;
        fMessage = message;
        fData = data;
    }

    // Public API
    public String getStatus() {
        return fStatus;
    }

    public void setStatus(String status) {
        fStatus = status;
    }

    public String getMessage() {
        return fMessage;
    }

    public void setMessage(String message) {
        fMessage = message;
    }

    public T getData() {
        return fData;
    }

    public void setData(T data) {
        fData = data;
    }

    public static <T> ServerResponse<T> success(String message, T data) {
        return new ServerResponse<>("SUCCESS", message, data);
    }

    public static <T> ServerResponse<T> error(String message) {
        return new ServerResponse<>("ERROR", message, null);
    }

}