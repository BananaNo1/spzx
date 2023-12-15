package com.leis.user.service;

import com.leis.model.dto.h5.UserLoginDto;
import com.leis.model.dto.h5.UserRegisterDto;
import com.leis.model.vo.h5.UserInfoVo;

public interface IUserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);
}
