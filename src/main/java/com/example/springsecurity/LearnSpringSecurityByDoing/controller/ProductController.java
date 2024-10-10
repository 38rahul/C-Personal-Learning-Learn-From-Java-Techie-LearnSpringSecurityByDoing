package com.example.springsecurity.LearnSpringSecurityByDoing.controller;

import com.example.springsecurity.LearnSpringSecurityByDoing.Entity.Product;
import com.example.springsecurity.LearnSpringSecurityByDoing.models.UserInfo;
import com.example.springsecurity.LearnSpringSecurityByDoing.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/welcome")
    public String welcome(){
        return  "welcome, This endpoint is not secured";
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("hasRole('ADMIN')") == @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllProducts(){
        return service.getProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public Product getProductById(@PathVariable int id){

        return service.getProduct(id);
    }

    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return  service.addUser(userInfo);
    }




}
