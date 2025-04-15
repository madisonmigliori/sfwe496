package com.sfwe496.gatewayserver.utils;

public class UserContext {

    public static final String CORRELATION_ID = "Correlation-ID";
    public static final String AUTH_TOKEN = "Auth-Token";

    private String correlationId;
    private String authToken;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
