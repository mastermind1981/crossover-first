package com.aurea.deadcode.detector.infra.exception;

import java.util.Map;

public class RepositoryNotFoundException extends GeneralException {

    public RepositoryNotFoundException(String message) {
        super(message);
    }

    public RepositoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public RepositoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RepositoryNotFoundException(Map<String, String> errorMsgs, String message, Throwable cause) {
        super(errorMsgs, message, cause);
    }

    public RepositoryNotFoundException(Map<String, String> errorMsgs, String message) {
        super(errorMsgs, message);
    }
}