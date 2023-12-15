package com.product.service;

import com.github.pagehelper.PageInfo;
import com.leis.model.dto.h5.ProductSkuDto;
import com.leis.model.entity.product.ProductSku;
import com.leis.model.vo.h5.ProductItemVo;

import java.util.List;

public interface IProductService {


    List<ProductSku> findProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);
}
