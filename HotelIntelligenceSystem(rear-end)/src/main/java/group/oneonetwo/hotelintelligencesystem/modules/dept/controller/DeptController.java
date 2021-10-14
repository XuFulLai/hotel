package group.oneonetwo.hotelintelligencesystem.modules.dept.controller;

import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
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
@RestController
@RequestMapping("api/dept")
public class DeptController {

    @Autowired
    IDeptService deptService;

    @PostMapping("batchAdd")
    public Reply batchAdd(@RequestBody List<DeptVO> deptVOS){
        return deptService.batchAdd(deptVOS);
    }

}
