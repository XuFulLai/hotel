package group.oneonetwo.hotelintelligencesystem.modules.room.controller;

import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import group.oneonetwo.hotelintelligencesystem.modules.room.service.IRoomService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags="房间相关接口")
@RestController
@RequestMapping("api/room")
public class RoomController {
    @Autowired
    IRoomService roomService;


    @PostMapping("add")
    @ApiOperation("增加房间")
    public Reply<RoomVO> add(@RequestBody RoomVO roomVO){
        return Reply.success(roomService.add(roomVO));
    }

    @GetMapping("delete/{id}")
    @ApiOperation("删除房间")
    public  Reply deleteById(@PathVariable("id") String id){
        return roomService.deleteById(id)>0?Reply.success() :Reply.failed();
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据id查找房间")
    public Reply<RoomVO> selectById(@PathVariable("id") String id){
        return Reply.success(roomService.selectOneByIdReturnVO(id));
    }

    @PostMapping("modify")
    @ApiOperation("更改房间")
    public Reply<RoomVO> modify(@RequestBody RoomVO roomVO){
        return Reply.success(roomService.saveone(roomVO));
    }

}

