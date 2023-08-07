package com.Auton.gibg.service.CategoryService;

import com.Auton.gibg.entity.CategoryEntity.ProductCategory;
import com.Auton.gibg.repository.repostoryCategory.ProductRepositoryCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceCategoryImpl implements ProductServiceCategory {

    @Autowired
    private ProductRepositoryCategory catRepository;

    @Override
    public List<ProductCategory> findAll() {
        return catRepository.findAll();
    }

    @Override
    public ProductCategory create(ProductCategory productCategory) {
        return catRepository.save(productCategory);
    }

    @Override
    public boolean delete(Long id) {
        catRepository.deleteById(id);
        return false;
    }

    @Override
    public ProductCategory findById(Long id) {
        return catRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductCategory> findByParentId(Long parentCategoryId) {
        return catRepository.findByParentId(parentCategoryId);
    }
}