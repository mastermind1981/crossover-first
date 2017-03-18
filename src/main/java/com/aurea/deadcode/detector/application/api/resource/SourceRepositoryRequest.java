package com.aurea.deadcode.detector.application.api.resource;

import com.aurea.deadcode.detector.infra.rest.AbstractResource;
import lombok.Value;

@Value
public class SourceRepositoryRequest extends AbstractResource {

    private String name;
    private String repositoryUrl;
    private String language;

}
