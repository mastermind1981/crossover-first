package com.aurea.deadcode.detector.test.application.service;

import br.com.six2six.fixturefactory.Fixture;
import com.aurea.deadcode.detector.application.service.SourceRepositoryService;
import com.aurea.deadcode.detector.application.service.provider.SourceRepositoryServiceProvider;
import com.aurea.deadcode.detector.domain.model.SourceRepository;
import com.aurea.deadcode.detector.domain.repository.SourceRepositorySpringRepository;
import com.aurea.deadcode.detector.domain.to.SourceRepositoryTO;
import com.aurea.deadcode.detector.infra.exception.InvalidGithubUrlRepositoryException;
import com.aurea.deadcode.detector.infra.exception.UnsupportedLanguagueException;
import com.aurea.deadcode.detector.test.support.TestFixtureSupport;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

public class SourceRepositoryServiceTest extends TestFixtureSupport {

    @Mock
    private SourceRepositorySpringRepository repository;
    @Mock
    private ApplicationEventPublisher publisher;
    @InjectMocks
    private SourceRepositoryService service = new SourceRepositoryServiceProvider(repository, publisher);

    @Override
    public void setUp() {

    }

    @Test(expected = UnsupportedLanguagueException.class)
    public void shouldntCreateANewRepositoryGivenAnUnsupportedLanguage() {
        service.create(givenAnRequestWithAnUnsupportedLanguage());
    }

    @Test(expected = InvalidGithubUrlRepositoryException.class)
    public void shouldntCreateANewRepositoryGivenAnInvalidGithubUrl() {
        service.create(givenAnRequestWithAnInvalidGithubUrl());
    }

    @Test
    public void create() {
        final SourceRepositoryTO to = givenAnValidRequest();

        when(repository.findByUrl(to.getRepositoryUrl()))
                .thenReturn(null);

        when(repository.findUnprocessedRepositories())
                .thenReturn(getValidToProcessSourceRepositories());

        service.create(to);
    }

    @Test
    public void createAnExistingSourceRepository() {
        final SourceRepositoryTO to = givenAnValidRequest();
        final SourceRepository entity = givenAnValidSourceRepository();

        when(repository.findByUrl(to.getRepositoryUrl()))
                .thenReturn(entity);

        when(repository.findUnprocessedRepositories())
                .thenReturn(Fixture.from(SourceRepository.class).gimme(5, "validToProcess"));

        service.create(to);
    }

    private SourceRepositoryTO givenAnRequestWithAnUnsupportedLanguage() {
        return Fixture.from(SourceRepositoryTO.class).gimme("unsupportedLanguage");
    }

    private SourceRepositoryTO givenAnRequestWithAnInvalidGithubUrl() {
        return Fixture.from(SourceRepositoryTO.class).gimme("invalidGithubUrl");
    }

    private SourceRepositoryTO givenAnValidRequest() {
        return Fixture.from(SourceRepositoryTO.class).gimme("valid");
    }

    private SourceRepository givenAnValidSourceRepository() {
        return Fixture.from(SourceRepository.class).gimme("valid");
    }

    private List<SourceRepository> getValidToProcessSourceRepositories() {
        List<SourceRepository> repositories = Fixture.from(SourceRepository.class).gimme(5, "validToProcess");
        return repositories;
    }

}