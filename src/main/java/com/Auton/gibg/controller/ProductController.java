package com.Auton.gibg.controller;

import com.Auton.gibg.entity.Product;
import com.Auton.gibg.invalid.InvalidRequestException;
import com.Auton.gibg.repository.ProductRepository;
import com.Auton.gibg.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> productList = productService.findAll();
        return ResponseEntity.ok(productList);
    }
    @PostMapping("/create")
    public Product product(@RequestBody Product product){

        return productService.create(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (productService.existsById(id)) {
            productService.delete(id);
            return ResponseEntity.ok("ลบข้อมูลแล้ว");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("ไม่พบไอดีนี้");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long id, @RequestBody Product updatedProduct) {
        boolean isUpdated = productService.updateProduct(id, updatedProduct);
        if (isUpdated) {
            return ResponseEntity.ok("อัปเดตข้อมูลสินค้าสำเร็จ");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบสินค้าที่ต้องการอัปเดต");
        }
    }
    @PostMapping("/{productId}/images")
    public ResponseEntity<String> insertProductImage(@PathVariable("productId") Long productId, @RequestParam("imagePro") String imagePro, @RequestParam("imagePreview") String imagePreview) {
        Product updatedProduct = productService.insertProductImage(productId, imagePro, imagePreview);
        if (updatedProduct == null) {
            // ไม่พบสินค้าที่ต้องการแทรกภาพ
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Product image inserted successfully");
    }
    @DeleteMapping("/{productId}/images")
    public ResponseEntity<String> deleteProductImage(@PathVariable("productId") Long productId) {
        Product updatedProduct = productService.deleteProductImage(productId);
        if (updatedProduct == null) {
            // ไม่พบสินค้าที่ต้องการลบรูปภาพ
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Product image deleted successfully");
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId) {
        Optional<Product> product = productService.findProductById(productId);
        if (product.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(product.get());
        } else {
            // ไม่พบสินค้าที่ต้องการค้นหา
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/shops/{shopId}/products")
    public ResponseEntity<List<Product>> getProductsByShopId(@PathVariable("shopId") Long shopId) {
        List<Product> products = productService.findProductsByShopId(shopId);
        if (products.isEmpty()) {
            // ไม่พบสินค้าในร้านค้าที่ระบุ
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
    @GetMapping("/users/{userId}/products")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable("userId") Long userId) {
        List<Product> products = productService.findProductsByUserId(userId);
        if (products.isEmpty()) {
            // ไม่พบสินค้าที่เกี่ยวข้องกับผู้ใช้ที่ระบุ
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


}
