package com.boulderit.service;

import com.boulderit.model.Comment;
import com.boulderit.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findByProblemId(String problemId) {
        return commentRepository.findByProblemId(problemId);
    }

    @Override
    public Comment findById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Override
    public Comment save(Comment comment) {
        comment.setCreatedAt(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public Comment incrementUpvote(String id) {
        Comment comment = findById(id);
        Integer currentUpvotes = comment.getUpvotes() != null ? comment.getUpvotes() : 0;
        comment.setUpvotes(currentUpvotes + 1);
        return commentRepository.save(comment);
    }

    @Override
    public Comment decrementUpvote(String id) {
        Comment comment = findById(id);
        Integer currentUpvotes = comment.getUpvotes() != null ? comment.getUpvotes() : 0;
        comment.setUpvotes(Math.max(0, currentUpvotes - 1));  // Prevent negative votes
        return commentRepository.save(comment);
    }

    @Override
    public Comment incrementFlag(String id) {
        Comment comment = findById(id);
        Integer currentFlags = comment.getFlags() != null ? comment.getFlags() : 0;
        comment.setFlags(currentFlags + 1);
        return commentRepository.save(comment);
    }
}
