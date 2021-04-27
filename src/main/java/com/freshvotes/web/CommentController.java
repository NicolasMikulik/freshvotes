package com.freshvotes.web;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.CommentRepository;
import com.freshvotes.repositories.FeatureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/products/{productId}/features/{featureId}/comments")
public class CommentController {

    @Autowired
    public CommentRepository commentRepo;

    @Autowired
    public FeatureRepository featureRepo;

    @GetMapping("")
    @ResponseBody
    public List<Comment> getComments(@PathVariable Long featureId) {
        return commentRepo.findByFeatureId(featureId);
    }

    @PostMapping("")
    public String postComment(@AuthenticationPrincipal User user, @PathVariable Long featureId,
            @PathVariable Long productId, Comment comment) {
        Optional<Feature> featureOpt = featureRepo.findById(featureId);
        if (featureOpt.isPresent())
            comment.setFeature(featureOpt.get());
        comment.setUser(user);
        comment.setCreatedDate(new Date());

        commentRepo.save(comment);
        return "redirect:/products/" + productId + "/features/" + featureId;
    }
}
