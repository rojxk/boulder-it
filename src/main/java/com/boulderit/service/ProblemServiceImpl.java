package com.boulderit.service;

import com.boulderit.model.Problem;
import com.boulderit.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Problem save(Problem problem) {
        return problemRepository.save(problem);
    }

    @Override
    public Problem update(Problem problem) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public List<Problem> findBySectorId(String id) {
        return problemRepository.findBySectorId(id);
    }

    @Override
    public Problem incrementHarderVote(String id) {
        Problem problem = findById(id);
        if (problem.getGrades().getConsensus() == null) {
            Problem.Grades.Consensus consensus = new Problem.Grades.Consensus();
            consensus.setHarder(1);
            consensus.setEasier(0);
            problem.getGrades().setConsensus(consensus);
        } else {
            Integer currentVotes = problem.getGrades().getConsensus().getHarder();
            problem.getGrades().getConsensus().setHarder(currentVotes + 1);
        }
        return problemRepository.save(problem);
    }

    @Override
    public Problem incrementEasierVote(String id) {
        Problem problem = findById(id);
        if (problem.getGrades().getConsensus() == null) {
            Problem.Grades.Consensus consensus = new Problem.Grades.Consensus();
            consensus.setHarder(0);
            consensus.setEasier(1);
            problem.getGrades().setConsensus(consensus);
        } else {
            Integer currentVotes = problem.getGrades().getConsensus().getEasier();
            problem.getGrades().getConsensus().setEasier(currentVotes + 1);
        }
        return problemRepository.save(problem);
    }

    @Override
    public Problem incrementSendVote(String id) {
        Problem problem = findById(id);
        if (problem.getStatistics() == null) {
            Problem.Statistics statistics = new Problem.Statistics();
            statistics.setSendCount(1);
            statistics.setLikes(0);
            statistics.setProjectCount(0);
            problem.setStatistics(statistics);
        } else {
            Integer currentVotes = problem.getStatistics().getSendCount();
            problem.getStatistics().setSendCount(currentVotes + 1);
        }
        return problemRepository.save(problem);
    }

    @Override
    public Problem incrementProjectVote(String id) {
        Problem problem = findById(id);
        if (problem.getStatistics() == null) {
            Problem.Statistics statistics = new Problem.Statistics();
            statistics.setSendCount(0);
            statistics.setLikes(0);
            statistics.setProjectCount(1);
            problem.setStatistics(statistics);
        } else {
            Integer currentVotes = problem.getStatistics().getProjectCount();
            problem.getStatistics().setProjectCount(currentVotes + 1);
        }
        return problemRepository.save(problem);
    }

    @Override
    public Problem incrementLikeVote(String id) {
        Problem problem = findById(id);
        if (problem.getStatistics() == null) {
            Problem.Statistics statistics = new Problem.Statistics();
            statistics.setSendCount(0);
            statistics.setLikes(1);
            statistics.setProjectCount(0);
            problem.setStatistics(statistics);
        } else {
            Integer currentVotes = problem.getStatistics().getLikes();
            problem.getStatistics().setLikes(currentVotes + 1);
        }
        return problemRepository.save(problem);
    }
}
