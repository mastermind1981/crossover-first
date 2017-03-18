package com.aurea.deadcode.detector.test.template.loader;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.aurea.deadcode.detector.domain.to.SourceRepositoryTO;

public class SourceRepositoryTOTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(SourceRepositoryTO.class).addTemplate("valid", new Rule() {
            {
                add("name", uniqueRandom("github-repository-01", "github-repository-02"));
                add("repositoryUrl", random("https://github.com/viniciusluisr/simple-functor.git", "https://github.com/viniciusluisr/improved-optional"));
                add("language", random("Java", "java", "JaVa"));
            }
        });

        Fixture.of(SourceRepositoryTO.class).addTemplate("unsupportedLanguage").inherits("valid", new Rule() {
            {
                add("language", random("C#", "Scala", "Elixir"));
            }

        });

        Fixture.of(SourceRepositoryTO.class).addTemplate("invalidGithubUrl").inherits("valid", new Rule() {
            {
                add("repositoryUrl", random("ftp://repository.source.com", "http://bitbucket.com/source", "github.com/viniciusluisr/simple-functor.git"));
            }

        });
    }
}
