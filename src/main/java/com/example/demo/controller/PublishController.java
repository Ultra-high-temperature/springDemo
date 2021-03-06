package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.exception.CustomErrorCode;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Permission;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.PermissionService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PermissionService permissionService;


    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(
            Model model,
            @PathVariable(name = "id") Integer id) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(value = "id", required = false) Integer id,
            HttpServletRequest request,
            Model model) {
        User user = (User) request.getSession().getAttribute("user");
        Permission userPermissionInfo = permissionService.findPermissionInfo(user.getId());
        if (user == null) {
            model.addAttribute("error", "you have not login");
            return "publish";
        }
        if(userPermissionInfo.getBantime()!=null){
            throw new CustomException(CustomErrorCode.Banned_in);
        }
        Question question = new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
        //user表内自增的id指的是用户ID
        question.setId(id);
        //根据ID是否存在决定是新增还是更新question
        questionService.createOrUpdate(question);
        //缺乏应有的返回值，新发布帖子时无法根据帖子id进行重定向。
        String tieUrl;
        if ("".equals(id) || id == null) {
            tieUrl = "redirect:/";
        }
        else {
            tieUrl = "redirect:/question/"+id;
        }
        return tieUrl;
    }
}
