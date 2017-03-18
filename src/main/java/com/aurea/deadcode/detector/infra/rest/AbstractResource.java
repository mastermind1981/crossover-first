package com.aurea.deadcode.detector.infra.rest;

import java.io.Serializable;
import java.util.UUID;

public class AbstractResource implements Serializable {

    private String requestUUID = UUID.randomUUID().toString();

    public String getRequestUUID() {
        return requestUUID;
    }
}