package com.leis.service.impl;

import com.leis.mapper.SysRoleMapper;
import com.leis.mapper.SysRoleMenuMapper;
import com.leis.model.dto.system.AssginMenuDto;
import com.leis.model.entity.system.SysMenu;
import com.leis.service.ISysMenuService;
import com.leis.service.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements ISysRoleMenuService {

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        List<SysMenu> sysMenuList = sysMenuService.findNodes();
        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);
        Map<String, Object> result = new HashMap<>();
        result.put("sysMenuList", sysMenuList);
        result.put("roleMenuIds", roleMenuIds);
        return result;
    }

    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());

        List<Map<String, Number>> menuInfo = assginMenuDto.getMenuIdList();
        if (menuInfo != null && menuInfo.size() > 0) {
            sysRoleMenuMapper.doAssign(assginMenuDto);
        }
    }
}
