package com.boulderit.repository;

import com.boulderit.model.Comment;
import com.boulderit.model.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    @Query("{ 'problem_id': ?0 }")
    List<Comment> findByProblemId(String problemId);

}
