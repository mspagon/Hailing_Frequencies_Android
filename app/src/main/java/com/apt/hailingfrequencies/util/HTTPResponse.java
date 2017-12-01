package com.apt.hailingfrequencies.util;

public class HTTPResponse {
    private String response;

    public HTTPResponse(String response) {
        this.response = response;
    }

    public HTTPResponse() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
