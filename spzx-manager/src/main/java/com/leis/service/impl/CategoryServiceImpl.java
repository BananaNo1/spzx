package com.leis.service.impl;

import com.alibaba.excel.EasyExcel;
import com.leis.exception.CustomException;
import com.leis.listener.ExcelListener;
import com.leis.mapper.CategoryMapper;
import com.leis.model.entity.product.Category;
import com.leis.model.vo.common.ResultCodeEnum;
import com.leis.model.vo.product.CategoryExcelVo;
import com.leis.service.ICategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findCategoryList(Long parentId) {
        List<Category> categoryList = categoryMapper.selectByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            categoryList.forEach(item -> {
                int count = categoryMapper.countByParentId(item.getId());
                if (count > 0) {
                    item.setHasChildren(true);
                } else {
                    item.setHasChildren(false);
                }
            });
        }
        return categoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            List<Category> categoryList = categoryMapper.selectAll();
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size());
            for (Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category, categoryExcelVo, CategoryExcelVo.class);
                categoryExcelVoList.add(categoryExcelVo);
            }
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).sheet("分类数据").doWrite(categoryExcelVoList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void importData(MultipartFile file) {
        try {
            ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(categoryMapper);

            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelListener).sheet().doRead();

        } catch (IOException e) {
            throw new CustomException(ResultCodeEnum.DATA_ERROR);
        }
    }

}
