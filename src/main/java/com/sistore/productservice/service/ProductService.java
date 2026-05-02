package com.sistore.productservice.service;

import com.sistore.productservice.dto.ProductRequest;
import com.sistore.productservice.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(ProductRequest product);

    List<Product> getAllProducts();

    Product deleteProduct(Long id);

    Product updateProduct(Long id, ProductRequest product);
}
