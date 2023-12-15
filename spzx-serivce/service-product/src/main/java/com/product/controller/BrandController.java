package com.product.controller;

import com.leis.model.entity.product.Brand;
import com.leis.model.vo.common.Result;
import com.leis.model.vo.common.ResultCodeEnum;
import com.product.service.IBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "品牌管理")
@RestController
@RequestMapping(value = "/api/product/brand")
@SuppressWarnings({"unchecked", "rawtypes"})
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @Operation(summary = "获取全部品牌")
    @GetMapping("findAll")
    public Result<List<Brand>> findALl() {
        List<Brand> list = brandService.findALl();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
