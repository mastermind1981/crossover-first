package com.aurea.deadcode.detector.test.template.loader;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.aurea.deadcode.detector.domain.model.ProcessingStatus;
import com.aurea.deadcode.detector.domain.model.SourceRepository;
import org.joda.time.DateTime;

import java.util.UUID;

public class SourceRepositoryTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(SourceRepository.class).addTemplate("valid", new Rule() {
            {
                add("id", UUID.randomUUID().toString());
                add("name", uniqueRandom("github-repository-01", "github-repository-02"));
                add("url", random("https://github.com/viniciusluisr/simple-functor.git",
                        "https://github.com/viniciusluisr/improved-optional"));
                add("udbPath", uniqueRandom("src/main/resources/analyze/udb/github-repository-01.udb",
                        "src/main/resources/analyze/udb/github-repository-02.udb"));
                add("status", random(ProcessingStatus.TO_PROCESS, ProcessingStatus.PROCESSING, ProcessingStatus.COMPLETED, ProcessingStatus.FAILED));
                add("startProcessingTime", random(DateTime.now().getMillis()));
                add("finishedProcessingTime", random(DateTime.now().plusMinutes(2).getMillis()));
            }
        });

        Fixture.of(SourceRepository.class).addTemplate("validToProcess").inherits("valid", new Rule() {
            {
                add("status", null);
                add("startProcessingTime", null);
                add("finishedProcessingTime", null);
            }

        });

    }
}