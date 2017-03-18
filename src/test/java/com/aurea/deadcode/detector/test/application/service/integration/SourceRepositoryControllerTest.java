package com.aurea.deadcode.detector.test.application.service.integration;

import br.com.six2six.fixturefactory.Fixture;
import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryPageResponse;
import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryRequest;
import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryResponse;
import com.aurea.deadcode.detector.domain.to.SourceRepositoryTO;
import com.aurea.deadcode.detector.test.support.TestApiEndpoints;
import com.aurea.deadcode.detector.test.support.TestFixtureSupport;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SourceRepositoryControllerTest extends TestFixtureSupport {

    @Override
    public void setUp() {

    }

    @Test
    public void createNewSourceRepository() {
        final SourceRepositoryTO to = Fixture.from(SourceRepositoryTO.class).gimme("valid");
        ResponseEntity<HttpStatus> status = TestApiEndpoints.create(new SourceRepositoryRequest(to.getName(), to.getRepositoryUrl(), to.getLanguage()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseEntity<SourceRepositoryPageResponse> response = TestApiEndpoints.listAll();
        SourceRepositoryPageResponse body = response.getBody();

        SourceRepositoryResponse found = body.getResponse().getContent().get(0);

        assertEquals(status.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(found.getUrl(), to.getRepositoryUrl());
        assertNotNull(found.getStatus());

        cleanAnalyzedFiles();
    }

    @Test
    public void listAllSourceRepositories() {
        final List<SourceRepositoryTO> tos = Fixture.from(SourceRepositoryTO.class).gimme(2, "valid");
        final List<ResponseEntity<HttpStatus>> statuses = new ArrayList<>();

        tos.forEach(t -> statuses.add(TestApiEndpoints.create(new SourceRepositoryRequest(t.getName(), t.getRepositoryUrl(), t.getLanguage()))));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        statuses.forEach(s -> assertEquals(s.getStatusCode(), HttpStatus.CREATED));

        ResponseEntity<SourceRepositoryPageResponse> response = TestApiEndpoints.listAll();

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        response.getBody().getResponse().forEach(r -> {
            assertNotNull(r.getStatus());
            assertNotNull(r.getId());
        });

        cleanAnalyzedFiles();
    }

    private void cleanAnalyzedFiles() {
        final CommandLine cmd = new CommandLine(System.getProperty("user.dir") + "/clean_analyzed_files.sh");
        final Executor exec = new DefaultExecutor();
        try {
            exec.execute(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
