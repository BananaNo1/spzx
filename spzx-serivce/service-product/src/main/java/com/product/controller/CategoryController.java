package com.product.controller;

import com.leis.model.entity.product.Category;
import com.leis.model.vo.common.Result;
import com.leis.model.vo.common.ResultCodeEnum;
import com.product.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "分类接口管理")
@RequestMapping(value = "/api/product/category")
@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Operation(summary = "获取分类树形数据")
    @GetMapping("/findCategoryTree")
    public Result<List<Category>> findCategoryTree() {
        List<Category> categoryList = categoryService.findCategoryTree();
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

}
