//package com.aurea.deadcode.detector.infra.exception.handler;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class RestErrorHandlerAspect {
//
//    private static final String CONTENT_TYPE = "Content-Type";
//    private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
//
//    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public ResponseEntity<String> handlerException(final Exception ex) {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add(CONTENT_TYPE, CONTENT_TYPE_JSON);
//        return new ResponseEntity<>(ex.getMessage(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//}
