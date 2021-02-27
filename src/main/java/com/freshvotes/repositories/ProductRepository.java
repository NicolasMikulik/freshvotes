package com.freshvotes.repositories;

import java.util.List;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // select * from product where user = :user
    List<Product> findByUser(User user);

    // select * from product where name = :name
    // List<Product> findByName(String name);
}
