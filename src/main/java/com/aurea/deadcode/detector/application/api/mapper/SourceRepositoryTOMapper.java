package com.aurea.deadcode.detector.application.api.mapper;

import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryRequest;
import com.aurea.deadcode.detector.domain.to.SourceRepositoryTO;
import com.aurea.deadcode.detector.infra.util.Mapper;

public class SourceRepositoryTOMapper implements Mapper<SourceRepositoryRequest, SourceRepositoryTO> {

    @Override
    public SourceRepositoryTO map(final SourceRepositoryRequest request) {

        return new SourceRepositoryTO(request.getName(),
                request.getRepositoryUrl(),
                request.getLanguage());
    }
}
