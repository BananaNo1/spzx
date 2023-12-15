package com.product.mapper;

import com.leis.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {

    List<Brand> findALl();

}
