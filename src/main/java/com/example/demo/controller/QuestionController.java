package com.example.demo.controller;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.model.Permission;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.NotificationService;
import com.example.demo.service.PermissionService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           @RequestParam(name = "noticeId",required = false,defaultValue = "0") long noticeId,
                           HttpServletRequest request,
                           Model model){
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            Permission userPermissionInfo = permissionService.findPermissionInfo(user.getId());
            if(userPermissionInfo.getBantime()!=null){
                model.addAttribute("banInfo","Banned_In");
            }
        }
        if(noticeId!=0){
            notificationService.isRead(noticeId);
        }
        List<CommentDTO> commentDTOList=commentService.ListByQuestionId(id, CommentTypeEnum.QUESTION.getType());
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuest=questionService.findRelatedQuestion(questionDTO.getTag());
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("relatedQuest",relatedQuest);
        model.addAttribute("commentDTOList",commentDTOList);
        return "question";
    }
}
