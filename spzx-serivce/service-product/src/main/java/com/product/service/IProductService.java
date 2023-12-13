package com.product.service;

import com.leis.model.entity.product.ProductSku;

import java.util.List;

public interface IProductService {


    List<ProductSku> findProductSkuBySale();

}
