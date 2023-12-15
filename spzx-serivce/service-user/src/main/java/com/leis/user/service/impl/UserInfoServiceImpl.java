package com.leis.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.leis.exception.CustomException;
import com.leis.model.dto.h5.UserLoginDto;
import com.leis.model.dto.h5.UserRegisterDto;
import com.leis.model.entity.user.UserInfo;
import com.leis.model.vo.common.ResultCodeEnum;
import com.leis.model.vo.h5.UserInfoVo;
import com.leis.user.mapper.UserInfoMapper;
import com.leis.user.service.IUserInfoService;
import com.leis.utils.AuthContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        String userName = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(code)) {
            throw new CustomException(ResultCodeEnum.DATA_ERROR);
        }

        String codeValueRedis = redisTemplate.opsForValue().get("phone:code:" + userName);
        if (!code.equals(codeValueRedis)) {
            throw new CustomException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        UserInfo userInfo = userInfoMapper.getByUsername(userName);
        if (null != userInfo) {
            throw new CustomException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        userInfo = new UserInfo();
        userInfo.setUsername(userName);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(userName);
        userInfo.setStatus(1);
        userInfo.setSex(0);

        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");

        userInfoMapper.save(userInfo);

        redisTemplate.delete("phone:code:" + userName);
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR);
        }

        UserInfo userInfo = userInfoMapper.getByUsername(username);
        if (null == userInfo) {
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR);
        }

        String md5InputPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (md5InputPassword.equals(userInfo)) {
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR);
        }

        //校验是否被禁用
        if (userInfo.getStatus() == 0) {
            throw new CustomException(ResultCodeEnum.ACCOUNT_STOP);
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:spzx:" + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }
}
