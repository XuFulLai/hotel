package group.oneonetwo.hotelintelligencesystem.modules.menu_dept.service;

import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.po.MenuDeptP0;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo.RoomTypeVO;

public interface IMenuDeptService {
    MenuDeptP0 selectOneById(String id);

    MenuDeptP0 add(RoomTypeVO roomTypeVO);

    MenuDeptP0 save(RoomTypeVO roomTypeVO);

    Integer deleteById(String id);

    MenuDeptVO selectOneByIdReturnVO(String id);
}
