package com.example.demo.controller;

import com.example.demo.dto.CommentCreateDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.exception.CustomErrorCode;
import com.example.demo.model.Comment;
import com.example.demo.model.Permission;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PermissionService permissionService;

    //@GetMapping @PostMapping都是组合注解 下行可视为 @PostMapping 的全名
    @ResponseBody//自动序列化发送到前端
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Permission userPermissionInfo = permissionService.findPermissionInfo(user.getId());
        if (commentCreateDTO == null) {
            return ResultDTO.errorOf(CustomErrorCode.CONTENT_IS_EMPTY);
        }
        if (user == null) {
            return ResultDTO.errorOf(CustomErrorCode.NO_LOGIN);
        }
        if(userPermissionInfo.getBantime()!=null){
            return ResultDTO.errorOf(CustomErrorCode.Banned_in);
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

    @ResponseBody//自动序列化发送到前端
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comment(@PathVariable(name = "id") Integer id) {
        List<CommentDTO> commentDTOList = commentService.ListByQuestionId(id, CommentTypeEnum.COMMENT.getType());
        return ResultDTO.okOf(commentDTOList);
    }
}
