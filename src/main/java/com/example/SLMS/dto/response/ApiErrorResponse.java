package com.example.SLMS.dto.response;

import java.time.Instant;

public class ApiErrorResponse {
    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String path;

    public ApiErrorResponse(int status, String error, String path) {
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
