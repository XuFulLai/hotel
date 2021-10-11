package group.oneonetwo.hotelintelligencesystem.modules.room_type.service;

import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.po.RoomTypePO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo.RoomTypeVO;

public interface IRoomTypeServeice {
    RoomTypePO selectOneById(String id);

    RoomTypePO add(RoomTypeVO roomTypeVO);

    RoomTypePO save(RoomTypeVO roomTypeVO);

    Integer deleteById(String id);

    RoomTypeVO selectOneByIdReturnVO(String id);
}
