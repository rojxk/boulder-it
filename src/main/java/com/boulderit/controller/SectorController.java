package com.boulderit.controller;

import com.boulderit.model.Comment;
import com.boulderit.model.Problem;
import com.boulderit.model.Sector;
import com.boulderit.service.CommentService;
import com.boulderit.service.ProblemService;
import com.boulderit.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {

    private ProblemService problemService;
    private SectorService sectorService;
    private CommentService commentService;

    @Autowired
    public SectorController(ProblemService problemService, SectorService sectorService, CommentService commentService){
        this.problemService = problemService;
        this.sectorService = sectorService;
        this.commentService = commentService;
    }

    @GetMapping("/{sectorId}")
    public ResponseEntity<Sector> findById(@PathVariable String sectorId){
        return ResponseEntity.ok(sectorService.findById(sectorId));
    }

    @GetMapping("/{sectorId}/problems")
    public ResponseEntity<Map<String, Object>> getProblemsBySector(@PathVariable String sectorId) {
        List<Problem> problems = problemService.findBySectorId(sectorId);
        Map<String, List<Comment>> commentsByProblem = new HashMap<>();

        // Get comments for each problem
        for (Problem problem : problems) {
            List<Comment> comments = commentService.findByProblemId(problem.getId());
            commentsByProblem.put(problem.getId(), comments);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("problems", problems);
        response.put("comments", commentsByProblem);

        return ResponseEntity.ok(response);
    }

}
