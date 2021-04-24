package com.freshvotes.repositories;

import java.util.List;

import com.freshvotes.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByFeatureId(Long featureId);

}
