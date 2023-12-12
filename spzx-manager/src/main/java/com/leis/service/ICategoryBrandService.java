package com.leis.service;

import com.github.pagehelper.PageInfo;
import com.leis.model.dto.product.CategoryBrandDto;
import com.leis.model.entity.product.CategoryBrand;

public interface ICategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Long id);

}
