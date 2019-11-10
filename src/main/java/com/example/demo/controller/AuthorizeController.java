package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDto;
import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.*;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Value("${github.Redirect_uri}")
    private String Redirect_uri;
    @Value("${github.Client_id}")
    private String Client_id;
    @Value("${github.Client_secret}")
    private String Client_secret;
    @Autowired
    private GithubProvider githubProvider;//把写了@Component的类自动实例化
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    //拦截回调请求
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        //从回调url中解析出code和state。spring自动获取上下文中的request以便获取session
        AccessTokenDto accessTokenDto = new AccessTokenDto();//ctrl+alt+v
        //新建数据中间件对象
        {
            accessTokenDto.setCode(code);
            accessTokenDto.setRedirect_uri(Redirect_uri);
            accessTokenDto.setState(state);
            accessTokenDto.setClient_id(Client_id);
            accessTokenDto.setClient_secret(Client_secret);
        }//给accessTokenDto赋值
        String access_token = githubProvider.getAccessToken(accessTokenDto);
        //获取access_token
        GithubUser githubUser = githubProvider.getUser(access_token);
        //根据已获得的access_token获取用户类
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccount_id(githubUser.getId().toString());
            user.setLogin_name(githubUser.getLogin());
            //user赋值
            userService.createOrUpdata(user);
            //根据用户是否存在，决定进行更新或者插入操作
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
            //重定向此界面
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }

    @GetMapping("/logout")//用户登出
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token", null);//清除cookie
        cookie.setMaxAge(0);//设置存活时间为0，立即消失
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }
}
