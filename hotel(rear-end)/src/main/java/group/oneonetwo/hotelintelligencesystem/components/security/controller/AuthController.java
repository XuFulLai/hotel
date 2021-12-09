package group.oneonetwo.hotelintelligencesystem.components.security.controller;

import group.oneonetwo.hotelintelligencesystem.components.security.entity.BaseUser;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.JwtTokenUtils;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu.service.IMenuService;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.tools.RedisUtil;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author 文
 * @description 权限的相关视图层
 */
@Api(tags = "权限")
@RestController
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IDeptService deptService;

    /**
     * 注册用户
     * @author 文
     * @param userVO
     * @return
     */
    @ApiOperation("注册用户")
    @PostMapping("open/auth/register")
    public Reply registerUser(@RequestBody UserVO userVO){
        return userService.register(userVO);
    }

    @PostMapping("/test")
    @ApiOperation("登录接口【POST】:\"/auth/login\"，需要参数:username,password")
    public void login(){

    }

    /**
     * 二维码登录相关
     * @param type 1为获取二维码,2为检查二维码状态
     * @return
     */
    @GetMapping("open/auth/qrCode/{type}")
    public Reply qrCode(@PathVariable("type") String type,String code) {
        switch (type) {
            case "1":
                String s = UUID.randomUUID().toString();
                redisUtil.set(s,"0");
                redisUtil.setKeyExpire(s,192000L);
                return Reply.success(s);
            case "2":
                HashMap<String, Object> map = new HashMap<>();
                String[] s1 = redisUtil.get(code).toString().split("-");
                String status = s1[0];
                map.put("status",status);
                if ("2".equals(status)) {
                    map.put("token",s1[1]);
                    String uid = JwtTokenUtils.getUsername(s1[1]);
                    UserVO userVO = userService.selectOneByIdReturnVO(uid);
                    MenuDeptVO vo = new MenuDeptVO();
                    vo.setRole(JwtTokenUtils.getUserRole(s1[1]));
                    vo.setDeptId(userVO.getDept());
                    List<MenuVO> menuTree = menuService.getMenuTreeByDeptIdAndRole(vo);
                    DeptVO deptVO = deptService.selectOneByIdReturnVO(userVO.getDept());
                    BaseUser baseUser = new BaseUser();
                    BeanUtils.copyProperties(userVO,baseUser);
                    baseUser.setRole(deptVO.getRole());
                    baseUser.setDeptName(deptVO.getName());
                    map.put("userInfo",baseUser);
                    map.put("menuList",menuTree);
                }
                return Reply.success(map);
            default:
                return Reply.failed("错误的类型码");
        }
    }

}
