package com.Auton.gibg.service;

import com.Auton.gibg.entity.Product;
import com.Auton.gibg.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> findAll(){
        return repository.findAll();
    }
    @Override
    public Product create(Product product) {
        return repository.save(product);
    }
    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }




    public boolean updateProduct(Long id, Product updatedProduct) {
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            // อัปเดตข้อมูลอื่นๆ ตามต้องการ

            repository.save(product);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public Product insertProductImage(Long productId, String imagePro, String imagePreview) {
        Optional<Product> optionalProduct = repository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setImagePro(imagePro);
            product.setImagePreview(imagePreview);
            return repository.save(product);
        } else {
            return null;
        }
    }
    @Override
    public Product deleteProductImage(Long productId) {
        Optional<Product> optionalProduct = repository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setImagePro(null);
            product.setImagePreview(null);
            return repository.save(product);
        } else {
            return null;
        }
    }
    @Override
    public Optional<Product> findProductById(Long productId) {
        return repository.findById(productId);
    }

    @Override
    public List<Product> findProductsByShopId(Long shopId) {
        return null;
    }

    @Override
    public List<Product> findProductsByUserId(Long userId) {
        return null;
    }

    @Override
    public void delete(Long id) {
    repository.deleteById(id);
    }


}

//    @Override
//    public Optional<Product> findById(Long id) {
//        return Optional.empty();
//    }
//    @Override
//    public List<Product> findProductsByShopId(Long shopId) {
//        return repository.findProductsByShopId(shopId);
//    }
//    @Override
//    public List<Product> findProductsByUserId(Long userId) {
//        return repository.findProductsByUserId(userId);
//    }




