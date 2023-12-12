package com.leis.service;


import com.github.pagehelper.PageInfo;
import com.leis.model.entity.product.ProductSpec;

public interface IProductSpecService {
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);

    void save(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void deleteById(Long id);
}
