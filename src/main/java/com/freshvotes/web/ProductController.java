package com.freshvotes.web;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/products")
    public String getProducts(ModelMap model) {
        return "product";
    }

    @GetMapping("/products/{productId}")
    public String getProduct(@PathVariable Long productId) {
        return "product";
    }

    @PostMapping("/products")
    public String createProducts(@AuthenticationPrincipal User user) {
        Product product = new Product(); // we are inserting a new row in the product table, when we create a new
                                         // product
        product.setPublished(false);
        product.setUser(user); // without product repository, spring boot takes the user NOT from the DB

        product = productRepo.save(product); // saves new product (writes into DB) and returns a new product with an ID
                                             // from DB

        return "redirect:/products/" + product.getId();
    }
}
