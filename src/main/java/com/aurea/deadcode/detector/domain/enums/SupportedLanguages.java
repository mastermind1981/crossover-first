package com.aurea.deadcode.detector.domain.enums;

import com.aurea.deadcode.detector.infra.exception.UnsupportedLanguagueException;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import static org.springframework.util.Assert.notNull;

public enum SupportedLanguages {

    JAVA("java");

    private String name;

    private SupportedLanguages(String name) {
        this.name = name;
    }

    public static void validateLanguage(final String language) {
        Arrays.stream(values())
            .filter(s -> language != null)
            .filter(s -> s.name.equals(language.toLowerCase()))
            .findFirst()
            .orElseThrow(() -> new UnsupportedLanguagueException("The given language is not supported by application"));
    }

    public String getName() {
        return name;
    }
}

