package group.oneonetwo.hotelintelligencesystem.components.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import group.oneonetwo.hotelintelligencesystem.components.security.dto.LoginUser;
import group.oneonetwo.hotelintelligencesystem.components.security.entity.JwtUser;
import group.oneonetwo.hotelintelligencesystem.components.security.utils.JwtTokenUtils;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 文
 * @description 登录认证过滤器(未携带token时进入此过滤器进行登录认证)
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    Logger logger = LoggerFactory.getLogger(Object.class);

    private AuthenticationManager authenticationManager;

//    private RedisUtil redisUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    /**
     * 身份验证
     * @author 文
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
//        //获取容器
//        ServletContext context = request.getServletContext();
//        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
//        assert ac != null;
//        redisUtil = ac.getBean(RedisUtil.class);

        // 从输入流中获取到登录的信息
        try {
            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (Exception e) {
            try{
                logger.error(e.toString() + " : " + e.getMessage());
                //返回json形式的错误信息
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                String reason = Reply.failed("202","用户名或密码错误",null).toString();
                response.getWriter().write(new ObjectMapper().writeValueAsString(reason));
                response.getWriter().flush();
            }catch (Exception ex){
                logger.error(e.toString() + " : " + e.getMessage());
            }
            return null;
        }
    }


    /**
     * 成功验证后调用的方法
     * 如果验证成功，就生成token并返回
     * @author 文
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

        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        logger.info("jwtUser:" + jwtUser.toString());

        String role = "";
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities){
            role = authority.getAuthority();
        }

        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), role);
        //写入redis(存入uid,role,username)
        Map<String,Object> map = new HashMap<>();
        map.put("uid",jwtUser.getId());
        map.put("role",role);
        map.put("username",jwtUser.getUsername());
//        redisUtil.hset(JwtTokenUtils.TOKEN_PREFIX + " " + token, (HashMap<String, Object>) map,1800000L);
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        // 往请求头中写入token,并返回信息(token,username,role)
        response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + " " + token);
        map.remove("uid");
        map.put("token",JwtTokenUtils.TOKEN_PREFIX + " " + token);
        map.put("username",JwtTokenUtils.getUsername(token));
        map.put("role",JwtTokenUtils.getUserRole(token));
        response.getWriter().write(Reply.success("登录成功",map).toString());

    }

    /**
     * 失败时调用的信息
     * @author 文
     * @param request
     * @param response
     * @param failed 错误信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write(Reply.failed("102",failed.getMessage(),null).toString());
    }
}
