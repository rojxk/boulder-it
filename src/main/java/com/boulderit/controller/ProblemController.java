package com.boulderit.controller;

import com.boulderit.model.Problem;
import com.boulderit.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/problems")
public class ProblemController {

    private ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService){
        this.problemService = problemService;
    }

    @PostMapping("/{id}/vote/harder")
    public ResponseEntity<Problem> voteHarder(@PathVariable String id) {
        Problem problem = problemService.incrementHarderVote(id);
        return ResponseEntity.ok(problem);
    }

    @PostMapping("/{id}/vote/easier")
    public ResponseEntity<Problem> voteEasier(@PathVariable String id){
        Problem problem = problemService.incrementEasierVote(id);
        return ResponseEntity.ok(problem);
    }

    @PostMapping("/{id}/vote/send")
    public ResponseEntity<Problem> voteSend(@PathVariable String id){
        Problem problem = problemService.incrementSendVote(id);
        return ResponseEntity.ok(problem);
    }

    @PostMapping("/{id}/vote/project")
    public ResponseEntity<Problem> voteProject(@PathVariable String id){
        Problem problem = problemService.incrementProjectVote(id);
        return ResponseEntity.ok(problem);
    }

    @PostMapping("/{id}/vote/like")
    public ResponseEntity<Problem> voteLike(@PathVariable String id){
        Problem problem = problemService.incrementLikeVote(id);
        return ResponseEntity.ok(problem);
    }




}
