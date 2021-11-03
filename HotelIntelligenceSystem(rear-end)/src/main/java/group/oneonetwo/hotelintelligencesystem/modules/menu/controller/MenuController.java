package group.oneonetwo.hotelintelligencesystem.modules.menu.controller;

import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu.service.IMenuService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author 文
 * @description 菜单控制层
 */
@Api(tags="菜单相关接口")
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    IMenuService menuService;

    @PostMapping("add")
    @ApiOperation("增加菜单")
    public Reply<MenuVO> add(@RequestBody MenuVO menuVO) {
        return Reply.success(menuService.add(menuVO));
    }

    @ApiOperation("删除菜单")
    @GetMapping("delete/{id}")
    public Reply deleteById(@PathVariable("id") String id) {
        return menuService.delete(id) > 0 ? Reply.success() : Reply.failed();
    }

    @ApiOperation("根据id查找菜单")
    @GetMapping("get/{id}")
    public Reply<MenuVO> selectById(@PathVariable("id") String id) {
        return Reply.success(menuService.selectByIdReturnVO(id));
    }

    @ApiOperation("获取菜单列表")
    @PostMapping("list")
    public Reply<List<MenuVO>> getTree(@RequestBody MenuVO menuVO) {
        return Reply.success(menuService.getMenuTree(menuVO));
    }

    @ApiOperation("更改菜单")
    @PostMapping("modify")
    public Reply<MenuVO> modify(@RequestBody MenuVO menuVO){
        return Reply.success(menuService.saveone(menuVO));
    }


}
