package com.filmflicks.utils;

public class Response {
    private String status;
    private String message;

    // Constructors
    public Response() {}

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
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

    // Static utility methods for common responses
    public static Response success(String message) {
        return new Response("success", message);
    }

    public static Response error(String message) {
        return new Response("error", message);
    }
}
