package com.leis.service;


import com.leis.model.entity.system.SysMenu;
import com.leis.model.vo.system.SysMenuVo;

import java.util.List;

public interface ISysMenuService {

    List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    void removeById(Long id);

    List<SysMenuVo> findUserMenuList();

}
