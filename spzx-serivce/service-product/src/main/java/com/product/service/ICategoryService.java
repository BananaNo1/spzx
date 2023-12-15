package com.product.service;

import com.leis.model.entity.product.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findOneCategory();

    List<Category> findCategoryTree();
    
}
