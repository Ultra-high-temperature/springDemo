package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDto;
import com.example.demo.dto.GithubUser;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/callback")
    //拦截回调请求
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        //从回调url中解析出code和state
        AccessTokenDto accessTokenDto = new AccessTokenDto();//ctrl+alt+v
        //新建数据中间件对象，下为赋值
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(Redirect_uri);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id(Client_id);
        accessTokenDto.setClient_secret(Client_secret);
        String access_token=githubProvider.getAccessToken(accessTokenDto);
        //获取access_token
        GithubUser User=githubProvider.getUser(access_token);
        //根据已获得的access_token获取用户类
        System.out.println(User.getLogin());
        return "index";
    }
}
