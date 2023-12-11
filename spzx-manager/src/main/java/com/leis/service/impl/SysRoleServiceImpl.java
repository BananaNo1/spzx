package com.leis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leis.mapper.SysRoleMapper;
import com.leis.model.dto.system.SysRoleDto;
import com.leis.model.entity.system.SysRole;
import com.leis.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo(sysRoleList);
        return pageInfo;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    @Override
    public Map<String, Object> findAllRoles() {
        List<SysRole> sysRoleList = sysRoleMapper.findAllRoles();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("allRoleList", sysRoleList);
        return resultMap;
    }

    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        List<SysRole> sysRoleList = sysRoleMapper.findAllRoles();
        List<Long> sysRoles = sysRoleMapper.findSysUserRoleByUserId(userId);
        Map<String, Object> resuleMap = new HashMap<>();
        resuleMap.put("allRolesList", sysRoleList);
        resuleMap.put("sysUserRoles", sysRoles);
        return resuleMap;
    }
}
