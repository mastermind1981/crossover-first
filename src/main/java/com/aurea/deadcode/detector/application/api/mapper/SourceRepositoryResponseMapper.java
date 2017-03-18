package com.aurea.deadcode.detector.application.api.mapper;

import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryResponse;
import com.aurea.deadcode.detector.domain.model.SourceRepository;
import com.aurea.deadcode.detector.infra.util.DateUtils;
import com.aurea.deadcode.detector.infra.util.Mapper;

import java.util.Optional;

public class SourceRepositoryResponseMapper implements Mapper<SourceRepository, SourceRepositoryResponse> {

    @Override
    public SourceRepositoryResponse map(final SourceRepository entity) {
        return new SourceRepositoryResponse(entity.getId(),
                entity.getUrl(),
                entity.getUdbPath(),
                entity.getStatus().toString(),
                getDate(entity.getStartProcessingTime()),
                getDate(entity.getFinishedProcessingTime()),
                entity.getOcurrences());
    }

    private String getDate(final Long timestamp) {
        return  Optional.ofNullable(timestamp)
                .map(DateUtils::getFormattedDateTime)
                .orElse(null);
    }
}
