package com.leis.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.leis.mapper.CategoryMapper;
import com.leis.model.vo.product.CategoryExcelVo;

import java.util.List;

public class ExcelListener<T> extends AnalysisEventListener<T> {

    private static final int BATCH_COUNT = 100;

    private List cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private CategoryMapper categoryMapper;

    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        CategoryExcelVo data = (CategoryExcelVo) t;
        cacheDataList.add(data);
        if (cacheDataList.size() >= BATCH_COUNT) {
            saveData();
            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    private void saveData() {
        categoryMapper.batchInsert(cacheDataList);
    }
}
