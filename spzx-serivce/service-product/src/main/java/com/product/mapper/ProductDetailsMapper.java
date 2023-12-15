package com.product.mapper;

import com.leis.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {
    ProductDetails getByProductId(Long productId);
}
