package com.aurea.deadcode.detector.infra.exception;

import java.util.Map;

/**
 * Created by vinicius on 2017-03-09.
 */
public class InvalidGithubUrlRepositoryException extends GeneralException {

    public InvalidGithubUrlRepositoryException(String message) {
        super(message);
    }

    public InvalidGithubUrlRepositoryException(Throwable cause) {
        super(cause);
    }

    public InvalidGithubUrlRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidGithubUrlRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidGithubUrlRepositoryException(Map<String, String> errorMsgs, String message, Throwable cause) {
        super(errorMsgs, message, cause);
    }

    public InvalidGithubUrlRepositoryException(Map<String, String> errorMsgs, String message) {
        super(errorMsgs, message);
    }
}
