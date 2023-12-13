package com.product.controller;

import com.leis.model.entity.product.Category;
import com.leis.model.entity.product.ProductSku;
import com.leis.model.vo.common.Result;
import com.leis.model.vo.common.ResultCodeEnum;
import com.leis.model.vo.h5.IndexVo;
import com.product.service.ICategoryService;
import com.product.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value = "/api/product/index")
@SuppressWarnings({"unchecked", "rawtypes"})
public class IndexController {


    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;


    @Operation(summary = "获取首页数据")
    @GetMapping
    public Result<IndexVo> findData() {
        List<Category> categoryList = categoryService.findOneCategory();
        List<ProductSku> productSkuList = productService.findProductSkuBySale();
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }
}
