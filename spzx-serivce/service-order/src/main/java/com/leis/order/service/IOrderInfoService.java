package com.leis.order.service;

import com.github.pagehelper.PageInfo;
import com.leis.model.dto.h5.OrderInfoDto;
import com.leis.model.entity.order.OrderInfo;
import com.leis.model.vo.h5.TradeVo;

public interface IOrderInfoService {
    TradeVo getTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus);
}
