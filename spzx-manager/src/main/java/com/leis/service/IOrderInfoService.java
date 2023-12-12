package com.leis.service;

import com.leis.model.dto.order.OrderStatisticsDto;
import com.leis.model.vo.order.OrderStatisticsVo;

public interface IOrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
