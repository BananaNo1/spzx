package com.product.mapper;

import com.leis.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    Product getById(Long productId);
}
