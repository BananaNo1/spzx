package com.leis.service;

import com.github.pagehelper.PageInfo;
import com.leis.model.dto.product.CategoryBrandDto;
import com.leis.model.entity.product.Brand;
import com.leis.model.entity.product.CategoryBrand;

import java.util.List;

public interface ICategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);

}
