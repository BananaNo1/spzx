package com.leis.service;


import com.leis.model.dto.system.AssginMenuDto;

import java.util.Map;

public interface ISysRoleMenuService {


    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);
}
