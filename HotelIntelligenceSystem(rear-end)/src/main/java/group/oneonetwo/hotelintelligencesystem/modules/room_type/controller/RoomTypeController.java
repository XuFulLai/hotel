package group.oneonetwo.hotelintelligencesystem.modules.room_type.controller;

import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.model.vo.RoomTypeVO;
import group.oneonetwo.hotelintelligencesystem.modules.room_type.service.IRoomTypeServeice;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "房间类型相关接口")
@RestController
@RequestMapping("api/roomType")
public class RoomTypeController {
    @Autowired
    IRoomTypeServeice roomTypeServeice;

    @PostMapping("add")
    @ApiOperation("增加床的类型")
    public Reply<RoomTypeVO> add(RoomTypeVO roomTypeVO){
        return Reply.success(roomTypeServeice.add(roomTypeVO));
    }

}
