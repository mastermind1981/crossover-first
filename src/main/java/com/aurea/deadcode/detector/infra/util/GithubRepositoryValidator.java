package com.aurea.deadcode.detector.infra.util;

import com.aurea.deadcode.detector.infra.exception.InvalidGithubUrlRepositoryException;

import java.util.regex.Pattern;

public class GithubRepositoryValidator {

    public static void validate(final String url) {
        boolean matche = Pattern.matches("^(https?|http)://github[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", url);
        if(!matche) {
            throw new InvalidGithubUrlRepositoryException("The given github repository url is invalid");
        }
    }
}
