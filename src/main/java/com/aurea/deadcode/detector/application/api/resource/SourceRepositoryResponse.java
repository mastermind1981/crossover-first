package com.aurea.deadcode.detector.application.api.resource;

import com.aurea.deadcode.detector.domain.enums.DeadcodeType;
import com.aurea.deadcode.detector.infra.rest.AbstractResource;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class SourceRepositoryResponse extends AbstractResource {

    private String id;
    private String url;
    private String udbPath;
    private String status;
    private String startProcessingTime;
    private String finishedProcessingTime;
    private Map<DeadcodeType, List<String>> deadCodeOcurrences;

}
