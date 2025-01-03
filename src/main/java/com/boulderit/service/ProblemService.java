package com.boulderit.service;

import com.boulderit.model.Problem;

import java.util.List;

public interface ProblemService {
    List<Problem> findAll();
    Problem findById(String id);
    Problem save(Problem problem);
    Problem update(Problem problem);
    void deleteById(String id);
    List<Problem> findBySectorId(String id);
    public Problem incrementHarderVote(String id);
    Problem incrementEasierVote(String id);

    Problem incrementSendVote(String id);

    Problem incrementProjectVote(String id);

    Problem incrementLikeVote(String id);
}
