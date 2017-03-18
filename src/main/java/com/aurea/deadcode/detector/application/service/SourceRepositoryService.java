package com.aurea.deadcode.detector.application.service;

import com.aurea.deadcode.detector.domain.model.SourceRepository;
import com.aurea.deadcode.detector.domain.to.SourceRepositoryTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SourceRepositoryService {

    Page<SourceRepository> listAll(final Pageable pageable);
    SourceRepository get(final String id);
    void create(final SourceRepositoryTO to);

}
