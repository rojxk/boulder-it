package com.boulderit.repository;

import com.boulderit.model.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends MongoRepository<Problem, String> {

    @Query("{ 'sector_id': ?0 }")
    List<Problem> findBySectorId(String sectorId);


}
