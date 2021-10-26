package group.oneonetwo.hotelintelligencesystem.modules.hotel.controller;


import com.baomidou.mybatisplus.extension.api.R;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.service.IHotelService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="酒店相关接口")
@RestController
@RequestMapping("api/hotel")
public class HotelCtroller {
    @Autowired
    IHotelService hotelService;

    @PostMapping("add")
    @ApiOperation("增加酒店")
    public Reply<HotelVO> add(@RequestBody HotelVO hotelVO){
        return Reply.success(hotelService.add(hotelVO));
    }



    @PostMapping("delete/{id}")
    @ApiOperation("删除酒店")
    public Reply deleteById(@PathVariable("id") String id){
        return hotelService.deleteById(id)>0 ? Reply.success():Reply.failed();
    }

    @PostMapping("modify")
    @ApiOperation("更改酒店")
    public Reply<HotelVO> modify(@RequestBody HotelVO hotelVO){
        return Reply.success(hotelService.save(hotelVO));
    }
}
