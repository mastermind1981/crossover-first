package com.aurea.deadcode.detector.infra.service.provider;

import com.aurea.deadcode.detector.domain.enums.DeadcodeType;
import com.aurea.deadcode.detector.domain.event.AnalyzeRepositoriesEvent;
import com.aurea.deadcode.detector.domain.model.ProcessingStatus;
import com.aurea.deadcode.detector.domain.model.SourceRepository;
import com.aurea.deadcode.detector.domain.repository.SourceRepositorySpringRepository;
import com.aurea.deadcode.detector.infra.service.DeadcodeDetectorProcessor;
import com.aurea.deadcode.detector.infra.service.understand.Analyzable;
import com.aurea.deadcode.detector.infra.service.understand.UnderstandDatabase;
import com.aurea.deadcode.detector.infra.service.understand.UnderstandDatabaseFactory;
import com.aurea.deadcode.detector.infra.service.understand.UnderstandUDBService;
import com.aurea.deadcode.detector.infra.util.PairTuple;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeadcodeDetectorProcessorProvider implements DeadcodeDetectorProcessor {

    private
    SourceRepositorySpringRepository repository;
    private List<Analyzable> analyzers;
    private UnderstandUDBService understandUDBService;
    private UnderstandDatabaseFactory factory;

    @Autowired
    public DeadcodeDetectorProcessorProvider(SourceRepositorySpringRepository repository,
                                             List<Analyzable> analyzers,
                                             UnderstandUDBService understandUDBService,
                                             UnderstandDatabaseFactory factory) {
        this.repository = repository;
        this.analyzers = analyzers;
        this.understandUDBService = understandUDBService;
        this.factory = factory;
    }

    @EventListener()
    @Override
    public Collection<SourceRepository> analyzeRepositories(final AnalyzeRepositoriesEvent event) {
        return event.getRepositories()
                .stream()
                    .map(this::prepareToProcess)
                    .map(this::startProcessing)
                    .map(this::callAnalyzers)
                    .collect(Collectors.toList());
    }

    private SourceRepository prepareToProcess(final SourceRepository entity) {
        final SourceRepository updated = new SourceRepository(entity.getId(),
                entity.getName(),
                entity.getUrl(),
                understandUDBService.createUDB(entity.getUrl(), entity.getName()),
                ProcessingStatus.TO_PROCESS,
                null,
                null,
                null);

        repository.save(updated);
        return updated;
    }

    private SourceRepository startProcessing(final SourceRepository entity) {
        final SourceRepository updated = new SourceRepository(entity.getId(),
                entity.getName(),
                entity.getUrl(),
                entity.getUdbPath(),
                ProcessingStatus.PROCESSING,
                DateTime.now().getMillis(),
                null,
                null);

        repository.save(updated);
        return updated;
    }

    private SourceRepository callAnalyzers(final SourceRepository entity) {
        final Map<DeadcodeType, List<String>> result = new HashMap<>();
        UnderstandDatabase db = factory.getDatabase("src/main/resources/analyze/repository/db.udb");
            analyzers.forEach(a -> {
                final PairTuple<DeadcodeType, List<String>> analyzed = a.analyze(db);
                result.put(analyzed.getX(), analyzed.getY());
            });

        try {
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final SourceRepository updated = new SourceRepository(entity.getId(),
                entity.getName(),
                entity.getUrl(),
                entity.getUdbPath(),
                ProcessingStatus.COMPLETED,
                entity.getStartProcessingTime(),
                DateTime.now().getMillis(),
                result);

        repository.save(updated);

        understandUDBService.deleteUDB(entity.getName(), entity.getUdbPath());

        return updated;
    }
}