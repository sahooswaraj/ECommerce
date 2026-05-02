package com.sistore.productservice.controller;

import com.sistore.productservice.dto.ProductRequest;
import com.sistore.productservice.dto.ProductResponse;
import com.sistore.productservice.entity.Product;
import com.sistore.productservice.exception.ProductNotFoundException;
import com.sistore.productservice.logging.LogExecutionTime;
import com.sistore.productservice.service.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    public ProductServiceImpl productService;

    @PostMapping
    @LogExecutionTime
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductRequest product){
        Product product1 = productService.saveProduct(product);
        ProductResponse productResponse = productService.mapToProductResponse(product1);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        List<Product> allProducts = productService.getAllProducts();
        if(allProducts.isEmpty()){
            throw new ProductNotFoundException("No Records Found", HttpStatus.NOT_FOUND);
        }
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product: allProducts) {
            productResponses.add(productService.mapToProductResponse(product));
        }
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        Product product = productService.deleteProduct(id);
        if(Objects.isNull(product)){
            throw new ProductNotFoundException("No Product Available to Delete", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productService.mapToProductResponse(product),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @LogExecutionTime
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody(required = true) ProductRequest product){
        Product product1 = productService.updateProduct(id, product);
        if(Objects.isNull(product1)){
            throw new ProductNotFoundException("No Product Available to Update", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productService.mapToProductResponse(product1), HttpStatus.OK);
    }
}
