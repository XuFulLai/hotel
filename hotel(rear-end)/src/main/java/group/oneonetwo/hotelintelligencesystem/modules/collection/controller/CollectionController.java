package group.oneonetwo.hotelintelligencesystem.modules.collection.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.collection.service.ICollectionService;
import group.oneonetwo.hotelintelligencesystem.modules.hotel.model.vo.HotelVO;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 文
 * @description: 收藏控制层
 * @date 2022/5/10 11:10
 */
@Api(tags="收藏相关接口")
@RestController
@RequestMapping("api/collection")
public class CollectionController {

    @Autowired
    ICollectionService collectionService;

    @ApiOperation("收藏/取消收藏")
    @GetMapping("collection/{hotelId}")
    public Reply collection(@PathVariable("hotelId") String id) {
        collectionService.collection(id);
        return Reply.success();
    }

    @ApiOperation("获取我的收藏")
    @PostMapping("myCollectionList")
    public Reply<Page<HotelVO>> myCollectionList(@RequestBody group.oneonetwo.hotelintelligencesystem.tools.Page page) {
        return Reply.success(collectionService.myCollectionList(page));
    }

}
