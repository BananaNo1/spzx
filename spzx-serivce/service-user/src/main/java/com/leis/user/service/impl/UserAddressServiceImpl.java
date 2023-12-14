package com.leis.cart.service.impl;

import com.leis.cart.mapper.UserAddressMapper;
import com.leis.cart.service.IUserAddressService;
import com.leis.model.entity.user.UserAddress;
import com.leis.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements IUserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        return userAddressMapper.findByUserId(userId);
    }
}
