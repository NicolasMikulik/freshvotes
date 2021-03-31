package com.freshvotes.repositories;

import java.util.List;
import java.util.Optional;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p join fetch p.user where p.id = :id")
    Optional<Product> findByIdWithUser(Long id);

    // select * from product where user = :user
    List<Product> findByUser(User user);

    // select * from product where name = :name
    // List<Product> findByName(String name);

    // this will (roughly) create a SQL statement select * from product where name =
    // :name
    Optional<Product> findByName(String name);
}
