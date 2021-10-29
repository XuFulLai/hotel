package group.oneonetwo.hotelintelligencesystem.modules.menu_dept.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.dao.MenuDeptMapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.po.MenuDeptPO;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("open/menuDept")
public class MenuDeptController {

//    @Autowired
//    MenuDeptMapper menuDeptMapper;
//
//    @PostMapping
//    public Reply<List<MenuDeptPO>> get() {
//        QueryWrapper<MenuDeptPO> wrapper = new QueryWrapper<>();
//        return Reply.success(menuDeptMapper.selectList(wrapper));
//    }

}
