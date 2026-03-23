package org.example.shared;

public class Response<T> {

    private String status;
    private String message;
    private T data;

    public Response() {
        status = "";
        message = "";
        data = null;
    }

    public Response(String status, String message, T data) {
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
    public static <T> Response<T> success(String message, T data) {
        return new Response<>("Success...", message, data);
    }

    public static <T> Response<T> failure(String message) {
        return new Response<>("Failure...", message, null);
    }
}
