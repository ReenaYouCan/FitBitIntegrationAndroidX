package com.fitbit.authentication;

public interface LogoutTaskCompletionHandler {
    void logoutSuccess();

    void logoutError(String message);
}
