package com.product.mapper;

import com.leis.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {


    List<ProductSku> findProductSkuBySale();

}
