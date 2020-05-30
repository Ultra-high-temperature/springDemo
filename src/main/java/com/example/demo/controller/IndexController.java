package com.example.demo.controller;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.exception.CustomErrorCode;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Permission;
import com.example.demo.model.User;
import com.example.demo.service.PermissionService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/")
    public String index(
            HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search) {

        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            Permission userPermissionInfo = permissionService.findPermissionInfo(user.getId());
            if(userPermissionInfo.getPermissions()>1){
                model.addAttribute("PermissionRank", userPermissionInfo.getPermissions());
            }
        }
        PaginationDTO paginationDTO = questionService.list(search,page, size);//后两个为无意义参数
        model.addAttribute("paginationDTO", paginationDTO);
        return "index";
    }
}
