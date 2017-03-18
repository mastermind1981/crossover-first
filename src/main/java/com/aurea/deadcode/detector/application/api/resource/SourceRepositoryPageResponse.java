package com.aurea.deadcode.detector.application.api.resource;

import com.aurea.deadcode.detector.infra.rest.AbstractResource;
import com.aurea.deadcode.detector.infra.rest.RestResponsePage;
import lombok.Value;
import org.springframework.data.domain.Page;

@Value
public class SourceRepositoryPageResponse extends AbstractResource {

    private RestResponsePage<SourceRepositoryResponse> response;

}
