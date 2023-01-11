package com.uberkautilya.upload.multipartfile.model;

public class Response {
    Long id;
    String message;

    public Response(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
