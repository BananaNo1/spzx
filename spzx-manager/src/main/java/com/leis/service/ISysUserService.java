package com.leis.service;

import com.github.pagehelper.PageInfo;
import com.leis.model.dto.system.AssginRoleDto;
import com.leis.model.dto.system.LoginDto;
import com.leis.model.dto.system.SysUserDto;
import com.leis.model.entity.system.SysUser;
import com.leis.model.vo.system.LoginVo;

public interface ISysUserService {

    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);

    void doAssign(AssginRoleDto assginRoleDto);
}
