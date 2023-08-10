package com.Auton.gibg.repository;

import com.Auton.gibg.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

   // void delete(long id);
   // void deleteById(long id);
//    List<Product> findProductsByShopId(Long shopId);
//    List<Product> findProductsByUserId(Long userId);


}
