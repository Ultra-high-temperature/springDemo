package com.example.demo.controller;

import com.example.demo.dto.CommentCreateDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.exception.CustomErrorCode;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    //@GetMapping @PostMapping都是组合注解 下行可视为 @PostMapping 的全名
    @ResponseBody//自动序列化发送到前端
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(commentCreateDTO==null|| StringUtils.isNoneBlank()){
            return ResultDTO.errorOf(CustomErrorCode.CONTENT_IS_EMPTY);
        }
        if(user==null){
            return ResultDTO.errorOf(CustomErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParent_id(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(comment.getGmt_create());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
