package group.oneonetwo.hotelintelligencesystem.components.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import group.oneonetwo.hotelintelligencesystem.components.security.dto.LoginUser;
import group.oneonetwo.hotelintelligencesystem.components.security.entity.BaseUser;
import group.oneonetwo.hotelintelligencesystem.components.security.entity.JwtUser;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.JwtTokenUtils;
import group.oneonetwo.hotelintelligencesystem.enums.ResultCode;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.impl.DeptServiceImpl;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu.service.impl.MenuServiceImpl;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.service.impl.MenuDeptServiceImpl;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.IUserService;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.impl.UserServiceImpl;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.support.WebApplicationContextUtils;


import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author ???
 * @description ?????????????????????(?????????token???????????????????????????????????????)
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    Logger logger = LoggerFactory.getLogger(Object.class);

    private AuthenticationManager authenticationManager;

    private IUserService userService;

    private JwtTokenUtils jwtTokenUtils;

//    private RedisUtil redisUtil;

    private MenuServiceImpl menuService;

    private IDeptService deptService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    /**
     * ????????????
     * @author ???
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
//        //????????????
//        ServletContext context = request.getServletContext();
//        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
//        assert ac != null;
//        redisUtil = ac.getBean(RedisUtil.class);

        // ???????????????????????????????????????
        try {
            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (Exception e) {
            try{
                logger.error(e.toString() + " : " + e.getMessage());
                //??????json?????????????????????
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                String reason = Reply.failed("202","????????????????????????",null).toString();
                response.getWriter().write(new ObjectMapper().writeValueAsString(Reply.failed(ResultCode.USERNAME_PWD_ERROR.getCode(),ResultCode.USERNAME_PWD_ERROR.getMsg(),null)));
                response.getWriter().flush();
            }catch (Exception ex){
                logger.error(e.toString() + " : " + e.getMessage());
            }
            return null;
        }
    }


    /**
     * ??????????????????????????????
     * ??????????????????????????????token?????????
     * @author ???
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        //????????????
        ServletContext context = request.getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
        assert ac != null;
        menuService = ac.getBean(MenuServiceImpl.class);
        userService = ac.getBean(UserServiceImpl.class);
        deptService = ac.getBean(DeptServiceImpl.class);
        jwtTokenUtils = ac.getBean(JwtTokenUtils.class);

        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        logger.info("jwtUser:" + jwtUser.toString());

        String role = "";
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities){
            role = authority.getAuthority();
        }

        String token = jwtTokenUtils.createToken(jwtUser.getId(), role);

        Map<String,Object> map = new HashMap<>();
        UserVO userVO = userService.selectOneByIdReturnVO(jwtUser.getId());
        DeptVO deptVO = deptService.selectOneByIdReturnVO(userVO.getDept());
        MenuDeptVO vo = new MenuDeptVO();
        vo.setDeptId(userVO.getDept());
        vo.setRole(role);
        List<MenuVO> menuTree = menuService.getMenuTreeByDeptIdAndRole(vo);

        BaseUser baseUser = new BaseUser();
        BeanUtils.copyProperties(userVO,baseUser);
        baseUser.setRole(role);
        baseUser.setDeptName(deptVO.getName());
        map.put("userInfo",baseUser);
        // ?????????????????????token
        // ?????????????????????token???????????????token
        // ??????jwt?????????????????????????????????????????? `Bearer token`
        // ?????????????????????token,???????????????(token,username,role)
        response.setHeader("token", jwtTokenUtils.TOKEN_PREFIX + " " + token);
        map.put("token",jwtTokenUtils.TOKEN_PREFIX + " " + token);
//        map.put("role",JwtTokenUtils.getUserRole(token));
        map.put("menuList",menuTree);
        Gson gson = new Gson();
        response.getWriter().write(Reply.success(ResultCode.USER_LOGIN_SUCCESS.getMsg(),gson.toJson(map)).toString());

    }

    /**
     * ????????????????????????
     * @author ???
     * @param request
     * @param response
     * @param failed ????????????
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write(Reply.failed(ResultCode.FAILED.getCode(),failed.getMessage(),null).toString());
    }
}
