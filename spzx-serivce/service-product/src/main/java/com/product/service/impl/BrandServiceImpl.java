package com.product.service.impl;

import com.leis.model.entity.product.Brand;
import com.product.mapper.BrandMapper;
import com.product.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Cacheable(value = "brandList", unless = "#result.size()==0")
    @Override
    public List<Brand> findALl() {
        return brandMapper.findALl();
    }
}
