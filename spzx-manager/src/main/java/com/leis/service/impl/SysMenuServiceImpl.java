package com.leis.service.impl;

import com.leis.exception.CustomException;
import com.leis.helper.MenuHelper;
import com.leis.mapper.SysMenuMapper;
import com.leis.mapper.SysRoleMapper;
import com.leis.mapper.SysRoleMenuMapper;
import com.leis.model.entity.system.SysMenu;
import com.leis.model.entity.system.SysUser;
import com.leis.model.vo.common.ResultCodeEnum;
import com.leis.model.vo.system.SysMenuVo;
import com.leis.service.ISysMenuService;
import com.leis.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenuList = sysMenuMapper.selectAll();
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return null;
        }
        List<SysMenu> treeList = MenuHelper.buildTree(sysMenuList);
        return treeList;
    }

    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.insert(sysMenu);

        updateSysRoleMenuIsHalf(sysMenu);
    }

    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {
        SysMenu parentMenu = sysMenuMapper.selectById(sysMenu.getParentId());
        if (parentMenu != null) {
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());
            updateSysRoleMenuIsHalf(parentMenu);
        }

    }

    @Override
    public void updateById(SysMenu sysMenu) {
        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public void removeById(Long id) {
        int count = sysMenuMapper.countByParentId(id);
        if (count > 0) {
            throw new CustomException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.deleteById(id);
    }

    @Override
    public List<SysMenuVo> findUserMenuList() {
        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();          // 获取当前登录用户的id

        List<SysMenu> sysMenuList = sysMenuMapper.selectListByUserId(userId) ;

        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        return this.buildMenus(sysMenuTreeList);
    }

    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {
        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }


}
