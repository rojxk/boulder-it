package com.boulderit.service;

import com.boulderit.model.Problem;
import com.boulderit.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService{

    private ProblemRepository problemRepository;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository){
        this.problemRepository = problemRepository;
    }

    @Override
    public List<Problem> findAll() {
        return problemRepository.findAll();
    }

    @Override
    public Problem findById(String id) {
        return problemRepository.findById(id).orElseThrow();
    }

    @Override
    public Problem save(Problem problem) {
        return null;
    }

    @Override
    public Problem update(Problem problem) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
