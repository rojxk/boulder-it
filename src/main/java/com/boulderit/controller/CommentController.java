package com.boulderit.controller;

import com.boulderit.model.Comment;
import com.boulderit.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> save(@RequestBody Comment comment){
        comment.setCreatedAt(new Date());
        comment.setUpvotes(0);
        comment.setFlags(0);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(comment));
    }

    @GetMapping("/{id}/upvote")
    public ResponseEntity<Comment> upvoteComment(@PathVariable String id) {
        Comment comment = commentService.incrementUpvote(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{id}/downvote")
    public ResponseEntity<Comment> downvoteComment(@PathVariable String id) {
        Comment comment = commentService.decrementUpvote(id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{id}/flag")
    public ResponseEntity<Comment> flagComment(@PathVariable String id) {
        return ResponseEntity.ok(commentService.incrementFlag(id));
    }

}
