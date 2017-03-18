package com.aurea.deadcode.detector.infra.exception;

import java.util.Map;

public class UnsupportedLanguagueException extends GeneralException {

    public UnsupportedLanguagueException(String message) {
        super(message);
    }

    public UnsupportedLanguagueException(Throwable cause) {
        super(cause);
    }

    public UnsupportedLanguagueException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedLanguagueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnsupportedLanguagueException(Map<String, String> errorMsgs, String message, Throwable cause) {
        super(errorMsgs, message, cause);
    }

    public UnsupportedLanguagueException(Map<String, String> errorMsgs, String message) {
        super(errorMsgs, message);
    }
}
