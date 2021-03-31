package com.eugene.web.forms;

public class FailError extends Result {
    private final String error;

    public FailError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
