package com.boulderit.service;


import com.boulderit.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findByProblemId(String problemId);
    Comment save(Comment comment);
    Comment findById(String id);
    Comment incrementUpvote(String id);
    Comment decrementUpvote(String id);
    Comment incrementFlag(String id);
}
