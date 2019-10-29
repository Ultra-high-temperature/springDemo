package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDto;
import com.example.demo.dto.GithubUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
//把此代码初始化到spring上下文，使用了这个注解能自动实例化放进一个池子里，可以轻松调用
//体现了Spring的IOC
public class GithubProvider {
    //如果参数超过两个，最好新建一个类
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        //JSON.toJSONString(accessTokenDto) 将 accessTokenDto 解析为String对象
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            //进行请求
            String string = response.body().string();
            //解析response转为Sting对象
            //string值长这样 access_token=b90d15014e5a9ad5f82d60e868d91106505a7765&scope=user&token_type=bearer
            String[] split=string.split("&");
            String access_token=split[0].split("=")[1];
            //从返回值中裁剪出access_token字段
            return access_token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client;
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            //将string转为 GithubUser类的githubUser对象
            return githubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
