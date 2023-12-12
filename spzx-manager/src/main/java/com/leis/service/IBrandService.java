package com.leis.service;

import com.github.pagehelper.PageInfo;
import com.leis.model.entity.product.Brand;

import java.util.List;

public interface IBrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    void save(Brand brand);

    void updataById(Brand brand);

    void deleteById(Long id);

    List<Brand> findAll();

}
