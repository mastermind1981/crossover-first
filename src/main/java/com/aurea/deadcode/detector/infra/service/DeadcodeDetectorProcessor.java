package com.aurea.deadcode.detector.infra.service;

import com.aurea.deadcode.detector.domain.event.AnalyzeRepositoriesEvent;
import com.aurea.deadcode.detector.domain.model.SourceRepository;

import java.util.Collection;

public interface DeadcodeDetectorProcessor {

    Collection<SourceRepository> analyzeRepositories(final AnalyzeRepositoriesEvent event);
}
