package group.oneonetwo.hotelintelligencesystem.modules.menu_dept.service;

import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.po.MenuDeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;

public interface IMenuDeptService {
    MenuDeptPO selectOneById(String id);

    MenuDeptPO add(MenuDeptVO menuDeptVO);

    MenuDeptPO save(MenuDeptVO menuDeptVO);

    Integer deleteAllByMenuId(String id);

    MenuDeptVO selectOneByIdReturnVO(String id);

//    List<MenuDeptVO> getMenuDeptByDeptId()

    Integer batchAdd(String menuId,String deptId);
}
