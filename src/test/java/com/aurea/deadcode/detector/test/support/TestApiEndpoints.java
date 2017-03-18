package com.aurea.deadcode.detector.test.support;


import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryPageResponse;
import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryRequest;
import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryResponse;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TestApiEndpoints {

    private static final String BASE_URL = "http://127.0.0.1:29011/api/v1/repositories";

    public static ResponseEntity<HttpStatus> create(final SourceRepositoryRequest request) {
        return new TestRestTemplate().postForEntity(BASE_URL, request, HttpStatus.class);
    }

    public static ResponseEntity<SourceRepositoryResponse> find(final String repositoryId) {
        return new TestRestTemplate().getForEntity(BASE_URL + "/" + repositoryId, SourceRepositoryResponse.class);
    }

    public static ResponseEntity<SourceRepositoryPageResponse> listAll() {
        return new TestRestTemplate().getForEntity(BASE_URL, SourceRepositoryPageResponse.class);
    }

}