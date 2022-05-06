package group.oneonetwo.hotelintelligencesystem.modules.room.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.order.model.vo.OrderVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.po.RoomPO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.CheckInVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.DetailVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRoomService {
    RoomPO selectOneById(String id);
    RoomVO add(RoomVO roomVO);
    RoomVO selectOneByIdReturnVO(String id);
    RoomPO save(RoomVO roomVO);
    Integer deleteById(String id);
    RoomVO saveone(RoomVO roomVO);
    Page<RoomVO> getPage(RoomVO roomVO);
    List<RoomVO> getAllList (RoomVO roomVO);
    DetailVO getDetail(String id);
    List<RoomVO> getRoomTypeList( RoomVO roomVO);
    OrderVO checkIn(CheckInVO checkInVO);

    String checkOut(String id);

    void assignRoom(RoomVO roomVO);

    void cancelRoom(RoomVO roomVO);

    List<String> getFloor();
}
