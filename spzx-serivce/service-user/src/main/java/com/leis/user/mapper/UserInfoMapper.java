package com.leis.user.mapper;

import com.leis.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    UserInfo getByUsername(String username);

    void save(UserInfo userInfo);

}
