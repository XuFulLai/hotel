package group.oneonetwo.hotelintelligencesystem.modules.discounts.controller;

import group.oneonetwo.hotelintelligencesystem.modules.discounts.model.vo.DiscountsVO;
import group.oneonetwo.hotelintelligencesystem.modules.discounts.service.IDiscountsService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags="折扣相关接口")
@RestController
@RequestMapping("api/discounts")
public class DiscountsController {

    @Autowired
    IDiscountsService discountsService;

    @PostMapping("add")
    @ApiOperation("增加折扣")
    public Reply<DiscountsVO> add(@RequestBody DiscountsVO discountsVO){
        return Reply.success(discountsService.add(discountsVO));
    }

    @ApiOperation("删除折扣")
    @GetMapping("delete/{id}")
    public Reply deleteById(@PathVariable("id") String id){
        return discountsService.deleteById(id)>0?Reply.success():Reply.failed();
    }

    @ApiOperation("根据id查找折扣")
    @GetMapping("get/{id}")
    public Reply<DiscountsVO> selectById(@PathVariable("id") String id){
        return Reply.success(discountsService.selectOneByIdReturnVO(id));
    }

    @ApiOperation("更改折扣")
    @PostMapping("modify")
    public Reply<DiscountsVO> modify(@RequestBody DiscountsVO discountsVO){
        return Reply.success(discountsService.saveone(discountsVO));
    }
}
