package group.oneonetwo.hotelintelligencesystem.modules.menu.service;

import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;

import java.util.List;

public interface IMenuService {
    MenuVO add(MenuVO vo);

    Integer delete(String id);

    List<MenuVO> getMenuTreeByDeptId(String id);

    MenuVO selectByIdReturnVO(String id);

    List<MenuVO> getMenuTree(MenuVO menuVO);
}
