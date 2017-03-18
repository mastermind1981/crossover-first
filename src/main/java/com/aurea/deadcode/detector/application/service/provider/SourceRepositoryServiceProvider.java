package com.aurea.deadcode.detector.application.service.provider;

import com.aurea.deadcode.detector.application.service.SourceRepositoryService;
import com.aurea.deadcode.detector.domain.enums.SupportedLanguages;
import com.aurea.deadcode.detector.domain.event.AnalyzeRepositoriesEvent;
import com.aurea.deadcode.detector.domain.model.ProcessingStatus;
import com.aurea.deadcode.detector.domain.model.SourceRepository;
import com.aurea.deadcode.detector.domain.repository.SourceRepositorySpringRepository;
import com.aurea.deadcode.detector.domain.to.SourceRepositoryTO;
import com.aurea.deadcode.detector.infra.exception.RepositoryNotFoundException;
import com.aurea.deadcode.detector.infra.func.Opt;
import com.aurea.deadcode.detector.infra.service.understand.UnderstandUDBService;
import com.aurea.deadcode.detector.infra.util.GithubRepositoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SourceRepositoryServiceProvider implements SourceRepositoryService {

    private SourceRepositorySpringRepository repository;
    private ApplicationEventPublisher publisher;

    @Autowired
    public SourceRepositoryServiceProvider(SourceRepositorySpringRepository repository,
                                           ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public Page<SourceRepository> listAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public SourceRepository get(final String id) {
        return Optional.ofNullable(repository.findOne(id)).orElseThrow(() -> new RepositoryNotFoundException("Github Source Repository not found with given id: " + id));
    }

    @Override
    public void create(final SourceRepositoryTO to) {
        SupportedLanguages.validateLanguage(to.getLanguage());
        GithubRepositoryValidator.validate(to.getRepositoryUrl());
        executeSavingSteps(to);
    }

    private void executeSavingSteps(final SourceRepositoryTO to) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {

            Opt.ofNullable(repository.findByUrl(to.getRepositoryUrl()))
                    .ifPresent()
                        .apply(r -> {
                            final SourceRepository newRep = createNewSourceRepository(to);
                            final SourceRepository sourceRepository = new SourceRepository(r.getId(),
                                    r.getName(),
                                    r.getUrl(),
                                    newRep.getUdbPath(),
                                    newRep.getStatus(),
                                    newRep.getStartProcessingTime(),
                                    newRep.getFinishedProcessingTime(),
                                    newRep.getOcurrences());

                            repository.save(sourceRepository);
                        })
                       .elseApply(() -> repository.save(createNewSourceRepository(to)));

            final AnalyzeRepositoriesEvent event = new AnalyzeRepositoriesEvent(repository.findUnprocessedRepositories());
            publisher.publishEvent(event);
        });

        executor.shutdown();

    }

    private SourceRepository createNewSourceRepository(final SourceRepositoryTO to) {
        return new SourceRepository(null,
                to.getName(),
                to.getRepositoryUrl(),
                null,
                ProcessingStatus.TO_PROCESS,
                null,
                null,
                null);
    }
}