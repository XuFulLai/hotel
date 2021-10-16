package group.oneonetwo.hotelintelligencesystem.modules.menu.service.impl;

import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.modules.menu.dao.MenuMapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.po.MenuPO;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu.service.IMenuService;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.service.IMenuDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 文
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class MenuServiceImpl implements IMenuService {

    @Autowired
    IMenuDeptService menuDeptService;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public MenuVO add(MenuVO vo) {
        // 处理节点层级
        if ( "0".equals(vo.getpId()) || vo.getpId() == null) {
            vo.setLevel(1);
        }else {
            MenuPO menuPO = menuMapper.selectById(vo.getpId());
            if (menuPO == null) {
                throw new CommonException("未找到此父菜单");
            }
            vo.setLevel(menuPO.getLevel() + 1);
        }
        MenuPO menuPO = new MenuPO();
        BeanUtils.copyProperties(vo,menuPO);
        int insert = menuMapper.insert(menuPO);
        int addNum = 0;

        // 插入权限表
        if (insert != 0) {
            addNum = menuDeptService.batchAdd(vo.getId(), vo.getDeptId());
        }
        if (addNum == 0 && (vo.getDeptId() != null || vo.getDeptId() != "")){
            throw new CommonException("插入异常");
        }
        return vo;
    }

    @Override
    public List<MenuVO> getMenuTreeByDeptId(String id) {
        // 获取该id有权限访问的所有菜单
        List<MenuVO> allMenu = menuMapper.getMenuTreeByDeptId(id);
        List<MenuVO> resMenu = new ArrayList<>();
        for (MenuVO item : allMenu) {
            if (!"0".equals(item.getpId())) {
                break;
            }
            MenuVO menuVO = addChild(item, allMenu);
            resMenu.add(menuVO);
        }
        return resMenu;
    }

    private MenuVO addChild(MenuVO current,List<MenuVO> allMenu){
        List<MenuVO> children = new ArrayList<>();
        for (MenuVO item : allMenu) {
            if (item.getpId().equals(current.getId())) {
                item = addChild(item,allMenu);
                children.add(item);
            }
        }
        current.setChildren(children);
        return current;
    }


}
