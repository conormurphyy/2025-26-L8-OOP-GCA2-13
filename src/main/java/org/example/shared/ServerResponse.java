package org.example.shared;

public class ServerResponse<T> {

    //Fields
    private String _fStatus;
    private String _fMessage;
    private T _fData;

    // Constructors
    public ServerResponse() {
        _fStatus = "error";
        _fMessage = "";
        _fData = null;
    }

    public ServerResponse(String status, String message, T data) {
       if(status == null || status.isEmpty())
       {
           throw new IllegalArgumentException("status must not be empty");
       }
        _fStatus = status;
        _fMessage = message;
        _fData = data;
    }

    // Public API
    public String getStatus() {
        return _fStatus;
    }

    public void setStatus(String status) {
        _fStatus = status;
    }

    public String getMessage() {
        return _fMessage;
    }

    public void setMessage(String message) {
        _fMessage = message;
    }

    public T getData() {
        return _fData;
    }

    public void setData(T data) {
        _fData = data;
    }

    public boolean isSuccess() {return "success".equals(_fStatus); }

    public static <T> ServerResponse<T> success(String message, T data) {
        return new ServerResponse<>("SUCCESS", message, data);
    }

    public static <T> ServerResponse<T> error(String message) {
        return new ServerResponse<>("ERROR", message, null);
    }

}