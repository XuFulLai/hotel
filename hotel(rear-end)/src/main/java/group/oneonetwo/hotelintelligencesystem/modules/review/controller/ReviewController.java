package group.oneonetwo.hotelintelligencesystem.modules.review.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.review.model.vo.ReviewVO;
import group.oneonetwo.hotelintelligencesystem.modules.review.service.ReviewService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liustart
 * @create 2022-04-19 22:24
 */

@RestController
@Api(tags="审批实体")
@RequestMapping("api/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;


    @PostMapping("add")
    @ApiOperation("增加审批信息")
    public Reply<ReviewVO> add(@RequestBody ReviewVO reviewVO){
        return Reply.success(reviewService.add(reviewVO));
    }

    @GetMapping("get/{id}")
    @ApiOperation("根据id查审批信息")
    public Reply<ReviewVO> getById(@PathVariable("id") String id) {
        return Reply.success(reviewService.selectOneByIdReturnVO(id));
    }

    @PostMapping("delete/{id}")
    @ApiOperation("删除审批信息")
    public Reply deleteById(@PathVariable("id") String id){
        return reviewService.deleteById(id)>0 ? Reply.success():Reply.failed();
    }

    @PostMapping("modify")
    @ApiOperation("更改审批信息")
    public Reply<ReviewVO> modify(@RequestBody ReviewVO reviewVO){
        return Reply.success(reviewService.save(reviewVO));
    }

    @ApiOperation("查询审批信息列表(分页)")
    @PostMapping("page")
    public Reply<Page<ReviewVO>> getPage(@RequestBody ReviewVO reviewVO){
        return Reply.success(reviewService.getPage(reviewVO));
    }

    @ApiOperation("判断提交审核接口1")
    @PostMapping("check")
    public void getCheck(@RequestBody ReviewVO reviewVO){
        reviewService.getCheck(reviewVO);
        return ;
    }

    @ApiOperation("判断提交审核接口2")
    @PostMapping("checkssss")
    public void getReviews(@RequestBody ReviewVO reviewVO){
        reviewService.getReviews(reviewVO);
        return ;
    }





}
