package com.aurea.deadcode.detector.application.api.endpoint;

import com.aurea.deadcode.detector.application.api.mapper.SourceRepositoryResponseMapper;
import com.aurea.deadcode.detector.application.api.mapper.SourceRepositoryTOMapper;
import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryPageResponse;
import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryRequest;
import com.aurea.deadcode.detector.application.api.resource.SourceRepositoryResponse;
import com.aurea.deadcode.detector.application.service.SourceRepositoryService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Github Source Repositories controller", description = "This API provides actions to register and find for analyzed github source repositories",
        basePath = "/api/v1/repositories", produces = "application/json")
@RequestMapping(value = "/api/v1/repositories")
@RestController
public class SourceRepositoryController {

    private final @NotNull SourceRepositoryService service;

    @ApiOperation(value = "Returns a Source Repository by id", response = SourceRepositoryResponse.class)
    @RequestMapping(value = "/{repositoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<SourceRepositoryResponse> find(@PathVariable(value = "repositoryId") final String repositoryId) {
        return new ResponseEntity<>(new SourceRepositoryResponseMapper().map(service.get(repositoryId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Returns all stored Source Repositories", response = SourceRepositoryResponse.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<SourceRepositoryPageResponse> listAll(@RequestParam(value = "page", defaultValue = "0") final Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "10") final Integer size) {
        return new ResponseEntity<>(new SourceRepositoryPageResponse(new SourceRepositoryResponseMapper().mapToRestResponsePage(service.listAll(new PageRequest(page, size)))), HttpStatus.OK);
    }

    @ApiOperation(value = "Create a new Github Source Repository register in order to analyze for deadcode occurrences", response = HttpStatus.class)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody final SourceRepositoryRequest request) {
        service.create(new SourceRepositoryTOMapper().map(request));
    }

}
