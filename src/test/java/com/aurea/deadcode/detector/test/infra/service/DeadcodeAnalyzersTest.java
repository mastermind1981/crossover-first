package com.aurea.deadcode.detector.test.infra.service;

import br.com.six2six.fixturefactory.Fixture;
import com.aurea.deadcode.detector.domain.event.AnalyzeRepositoriesEvent;
import com.aurea.deadcode.detector.domain.model.ProcessingStatus;
import com.aurea.deadcode.detector.domain.model.SourceRepository;
import com.aurea.deadcode.detector.domain.repository.SourceRepositorySpringRepository;
import com.aurea.deadcode.detector.infra.service.DeadcodeDetectorProcessor;
import com.aurea.deadcode.detector.infra.service.provider.DeadcodeDetectorProcessorProvider;
import com.aurea.deadcode.detector.infra.service.understand.Analyzable;
import com.aurea.deadcode.detector.infra.service.understand.UnderstandDatabase;
import com.aurea.deadcode.detector.infra.service.understand.UnderstandDatabaseFactory;
import com.aurea.deadcode.detector.infra.service.understand.UnderstandUDBService;
import com.aurea.deadcode.detector.test.support.TestFixtureSupport;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.xml.transform.Source;
import java.util.Collection;
import java.util.List;

public class DeadcodeAnalyzersTest extends TestFixtureSupport {

    @Mock
    private SourceRepositorySpringRepository repository;
    @Mock
    private List<Analyzable> analyzers;
    @Mock
    private UnderstandUDBService understandUDBService;
    @Mock
    private UnderstandDatabaseFactory factory;
    @Mock
    private UnderstandDatabase database;
    @InjectMocks
    private DeadcodeDetectorProcessor deadcodeDetectorProcessor =
            new DeadcodeDetectorProcessorProvider(repository, analyzers, understandUDBService, factory);

    @Override
    public void setUp() {

    }

    @Test
    public void testAnalyzeRepositories() {

        AnalyzeRepositoriesEvent event =
                new AnalyzeRepositoriesEvent(Fixture
                        .from(SourceRepository.class).gimme(2, "validToProcess"));

        when(factory.getDatabase(any(String.class))).thenReturn(database);

        final Collection<SourceRepository> result = deadcodeDetectorProcessor.analyzeRepositories(event);

        result.forEach(r -> {
            assertEquals(r.getStatus(), ProcessingStatus.COMPLETED);
            assertNotNull(r.getStartProcessingTime());
            assertNotNull(r.getFinishedProcessingTime());
        });

    }

}