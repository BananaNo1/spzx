package com.leis.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leis.exception.CustomException;
import com.leis.mapper.SysRoleUserMapper;
import com.leis.mapper.SysUserMapper;
import com.leis.model.dto.system.AssginRoleDto;
import com.leis.model.dto.system.LoginDto;
import com.leis.model.dto.system.SysUserDto;
import com.leis.model.entity.system.SysUser;
import com.leis.model.vo.common.ResultCodeEnum;
import com.leis.model.vo.system.LoginVo;
import com.leis.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {

        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();

        String redisCode = redisTemplate.opsForValue().get("user:login:validatecode:" + codeKey);
        if (StringUtils.isEmpty(redisCode) || !StringUtils.equalsIgnoreCase(redisCode, captcha)) {
            throw new CustomException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        redisTemplate.delete("user:login:validatecode:" + codeKey);

        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
        if (sysUser == null) {
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR);
        }

        String inputPassword = loginDto.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        if (!StringUtils.equals(sysUser.getPassword(), md5Password)) {
            //throw new RuntimeException("用户名或者密码错误");
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR);
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login" + token, JSON.toJSONString(sysUser), 30, TimeUnit.MINUTES);

        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login:" + token);
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }

    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysUserList = sysUserMapper.findByPage(sysUserDto);
        PageInfo pageInfo = new PageInfo(sysUserList);
        return pageInfo;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        SysUser dbSysUser = sysUserMapper.findByUserName(sysUser.getUserName());
        if (dbSysUser != null) {
            throw new CustomException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        String password = sysUser.getPassword();
        String digestPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(digestPassword);
        sysUser.setStatus(0);
        sysUserMapper.saveSysUser(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId);
    }

    @Transactional
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());

        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        roleIdList.forEach(roleId -> {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(), roleId);
        });
    }
}
