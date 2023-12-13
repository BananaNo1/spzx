package com.product.service.impl;

import com.product.mapper.CategoryMapper;
import com.leis.model.entity.product.Category;
import com.product.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {


    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> findOneCategory() {
        return categoryMapper.findOneCategory();
    }
}
