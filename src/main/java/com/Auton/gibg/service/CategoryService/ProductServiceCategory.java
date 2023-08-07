package com.Auton.gibg.service.CategoryService;

import com.Auton.gibg.entity.CategoryEntity.ProductCategory;

import java.util.List;

public interface ProductServiceCategory {
    List<ProductCategory> findAll();
    ProductCategory create(ProductCategory productCategory);
    boolean delete(Long id);

    ProductCategory findById(Long id);

    List<ProductCategory> findByParentId(Long parent_category_id );


}
