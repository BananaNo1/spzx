package com.leis.cart.service;

import com.leis.model.entity.user.UserAddress;

import java.util.List;

public interface IUserAddressService {
    List<UserAddress> findUserAddressList();

}
