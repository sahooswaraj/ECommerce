package com.sistore.productservice.service;

import com.sistore.productservice.dto.InventoryRequest;
import com.sistore.productservice.dto.InventoryResponse;
import com.sistore.productservice.dto.ProductRequest;
import com.sistore.productservice.dto.ProductResponse;
import com.sistore.productservice.entity.Inventory;
import com.sistore.productservice.entity.Product;
import com.sistore.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    public ProductRepository productRepository;

    @Override
    public Product saveProduct(ProductRequest product) {
        product.setCreatedAt(LocalDateTime.now());
        Product product1 = mapToProduct(product);
        return productRepository.save(product1);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (Objects.nonNull(product))
            productRepository.delete(product);
        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product currentProduct = productRepository.findById(id).orElse(null);
        if (Objects.nonNull(currentProduct)) {
            currentProduct.setName(productRequest.getName());
            currentProduct.setDescription(productRequest.getDescription());
            currentProduct.setPrice(productRequest.getPrice());
            currentProduct.setCategory(productRequest.getCategory());
            currentProduct.setBrand(productRequest.getBrand());
            currentProduct.setAvailable(productRequest.getAvailable());
            currentProduct.setInventory(updateInventory(currentProduct.getInventory(), productRequest.getInventory()));
            currentProduct.setUpdatedAt(LocalDateTime.now());
            productRepository.save(currentProduct);
        }
        return currentProduct;
    }

    public ProductResponse mapToProductResponse(Product product) {
        ProductResponse productDto = new ProductResponse();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory());
        productDto.setBrand(product.getBrand());
        productDto.setAvailable(product.getAvailable());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setUpdatedAt(product.getUpdatedAt());
        productDto.setInventory(mapToInventoryResponse(product.getInventory()));
        return productDto;
    }

    public Product mapToProduct(ProductRequest productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setBrand(productDto.getBrand());
        product.setAvailable(productDto.getAvailable());
        product.setCreatedAt(productDto.getCreatedAt());
        product.setUpdatedAt(productDto.getUpdatedAt());
        product.setInventory(mapToInventory(productDto.getInventory()));
        return product;
    }

    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        if (Objects.isNull(inventory)) {
            return null;
        }
        InventoryResponse inventoryResponse = new InventoryResponse();
        inventoryResponse.setInventoryId(inventory.getInventoryId());
        inventoryResponse.setStock(inventory.getStock());
        return inventoryResponse;
    }

    private Inventory mapToInventory(InventoryRequest inventoryRequest) {
        if (Objects.isNull(inventoryRequest)) {
            return null;
        }
        Inventory inventory = new Inventory();
        inventory.setStock(inventoryRequest.getStock());
        return inventory;
    }


    private Inventory updateInventory(Inventory currentInventory,InventoryRequest inventoryRequest) {
        if (Objects.isNull(inventoryRequest)) {
            return null;
        }
        currentInventory.setStock(inventoryRequest.getStock());
        return currentInventory;
    }
}
