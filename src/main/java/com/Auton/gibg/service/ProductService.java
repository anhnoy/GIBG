package com.Auton.gibg.service;

import com.Auton.gibg.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Product create(Product product);
    boolean existsById(Long id);
    boolean updateProduct(Long id, Product updatedProduct);
    Product insertProductImage(Long productId, String imagePro, String imagePreview);
    Product deleteProductImage(Long productId);
    Optional<Product> findProductById(Long productId);
    List<Product> findProductsByShopId(Long shopId);
    List<Product> findProductsByUserId(Long userId);
    void delete( Long id );

   // void deleteById(long id);


}
