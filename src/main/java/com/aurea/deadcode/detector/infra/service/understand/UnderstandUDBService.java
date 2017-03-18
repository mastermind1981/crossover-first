package com.aurea.deadcode.detector.infra.service.understand;

public interface UnderstandUDBService {

    String createUDB(final String repositoryUrl, final String udbName);
    void deleteUDB(final String repositoryUrl, final String udbName);
}
