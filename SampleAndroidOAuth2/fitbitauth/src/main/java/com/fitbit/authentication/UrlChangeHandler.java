package com.fitbit.authentication;


public interface UrlChangeHandler {
    void onUrlChanged(String newUrl);
    void onLoadError(int errorCode, CharSequence description);
}
