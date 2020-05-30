package com.example.demo.controller;


import com.example.demo.dto.ResultDTO;
import com.example.demo.exception.CustomErrorCode;
import com.example.demo.model.User;
import com.example.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @PostMapping("/ban/")
    @ResponseBody
    public ResultDTO<String> banUser(
            @RequestParam(value = "id") int userId,
            @RequestParam(value = "banTime") Integer time,
            HttpServletRequest request){
        User admin = (User) request.getSession().getAttribute("user");
        if(admin==null){
            return ResultDTO.errorOf(CustomErrorCode.NO_LOGIN);
        }
        Date banTime =GetAnyTime(time);
        int i = permissionService.banUser(admin.getId(), userId, banTime);
        if(i==1){
            return ResultDTO.okOf();
        }
        else {
            return ResultDTO.errorOf(123,"封禁失败");
        }
    }

    private static Date GetAnyTime(Integer t) {//获得基于当前时间的前几天后几天之类的工具
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, t);
        date = calendar.getTime();
        return date;
    }
}
