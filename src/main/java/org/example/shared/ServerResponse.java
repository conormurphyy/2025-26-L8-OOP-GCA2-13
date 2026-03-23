package org.example.shared;

public class ServerResponse<T> {

    private String status;
    private String message;
    private T data;

    public ServerResponse() {
        status = "";
        message = "";
        data = null;
    }

    public ServerResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // HELPERS:
    public static <T> ServerResponse<T> success(String message, T data) {
        return new ServerResponse<>("Success...", message, data);
    }

    public static <T> ServerResponse<T> failure(String message) {
        return new ServerResponse<>("Failure...", message, null);
    }
}
