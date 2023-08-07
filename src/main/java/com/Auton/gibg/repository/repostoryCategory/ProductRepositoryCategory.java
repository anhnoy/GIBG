package com.Auton.gibg.repository.repostoryCategory;

import com.Auton.gibg.entity.CategoryEntity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepositoryCategory extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByParentId(Long parentId);
}
