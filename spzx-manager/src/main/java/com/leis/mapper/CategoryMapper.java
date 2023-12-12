package com.leis.mapper;

import com.leis.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {


    List<Category> selectByParentId(Long parentId);

    int countByParentId(Long id);

    List<Category> selectAll();

    void batchInsert(List cacheDataList);
}
