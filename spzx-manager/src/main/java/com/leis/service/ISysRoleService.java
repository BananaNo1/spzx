package com.leis.service;

import com.github.pagehelper.PageInfo;
import com.leis.model.dto.system.SysRoleDto;
import com.leis.model.entity.system.SysRole;

import java.util.Map;

public interface ISysRoleService {
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    Map<String, Object> findAllRoles();

    Map<String, Object> findAllRoles(Long userId);
}
