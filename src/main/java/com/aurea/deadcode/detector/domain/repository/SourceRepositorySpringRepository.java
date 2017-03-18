package com.aurea.deadcode.detector.domain.repository;

import com.aurea.deadcode.detector.domain.model.SourceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SourceRepositorySpringRepository extends MongoRepository<SourceRepository, String> {

//    @Query("select sr from SourceRepository sr where sr.status = 'TO_PROCESS'")
    @Query("{ 'status' : 'TO_PROCESS' }")
    List<SourceRepository> findUnprocessedRepositories();
    SourceRepository findByUrl(final String url);

}
