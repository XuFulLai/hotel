package group.oneonetwo.hotelintelligencesystem.modules.room.controller;

import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="房间相关接口")
@RestController
@RequestMapping("api/room")
public class RoomController {
    @Autowired
    IRoomService roomService;
//    @PostMapping("add")
//    @ApiOperation("增加房间")
//    public Reply<RoomVO> add(@RequestBody RoomVO roomVO){
//        return Reply.success(roomService.insertOne(roomVO));
//    }

}

