package com.freshvotes.web;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // @GetMapping("/products")
    // public String getProducts(@AuthenticationPrincipal User user, ModelMap model)
    // {
    // return "products";
    // }

    @GetMapping("/products/{productId}")
    public String getProduct(@PathVariable Long productId, ModelMap model, HttpServletResponse response)
            throws IOException {
        Optional<Product> productOpt = productRepo.findById(productId); // findById returns an optional class
                                                                        // Product, which can potentially return
                                                                        // nothing (null), optional is a wrapper around
                                                                        // the product, gives us methods
        if (productOpt.isPresent()) { // if it is in DB
            Product product = productOpt.get();
            model.put("product", product);
        } else { // if product is not in DB
            response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id " + productId + " was not found.");
        }
        return "product";
    }

    @PostMapping("/products/{productId}")
    public String saveProduct(@PathVariable Long productId, Product product) {
        System.out.println(product);
        product = productRepo.save(product);
        return "redirect:/products/" + product.getId();
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
