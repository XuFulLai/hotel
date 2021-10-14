package group.oneonetwo.hotelintelligencesystem.modules.dept.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 文
 * @description dept操作类
 */
@Api(tags = "部门相关接口")
@RestController
@RequestMapping("api/dept")
public class DeptController {

    @Autowired
    IDeptService deptService;

    @PostMapping("batchAdd")
    @ApiOperation("批量插入")
    public Reply batchAdd(@RequestBody List<DeptVO> deptVOS){
        return deptService.batchAdd(deptVOS);
    }

    @PostMapping("page")
    @ApiOperation("获取分页列表")
    public Reply<Page<DeptVO>> getPage(@RequestBody DeptVO deptVO) {
        return Reply.success(deptService.getPage(deptVO));
    }

}
