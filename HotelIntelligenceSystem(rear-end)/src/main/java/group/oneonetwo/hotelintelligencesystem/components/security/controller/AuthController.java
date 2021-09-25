package group.oneonetwo.hotelintelligencesystem.components.security.controller;

import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 文
 * @description 权限的相关视图层
 */
@Api(tags = "权限")
@RestController
public class AuthController {

    @Autowired
    private IUserService userService;

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

}
