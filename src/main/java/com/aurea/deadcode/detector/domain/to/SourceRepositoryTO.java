package com.aurea.deadcode.detector.domain.to;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Value
public class SourceRepositoryTO implements Serializable {

    private String name;
    private String repositoryUrl;
    private String language;

}
