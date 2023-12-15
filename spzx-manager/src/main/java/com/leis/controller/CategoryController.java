package com.leis.controller;


import com.leis.model.entity.product.Category;
import com.leis.model.vo.common.Result;
import com.leis.model.vo.common.ResultCodeEnum;
import com.leis.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/product/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Operation(summary = "根据parentId获取下级节点")
    @GetMapping(value = "/findCategoryList/{parentId}")
    public Result<List<Category>> findByParentId(@PathVariable Long parentId) {
        List<Category> list = categoryService.findCategoryList(parentId);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }

    @PostMapping(value = "/importData")
    public Result importData(MultipartFile file) {
        categoryService.importData(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
