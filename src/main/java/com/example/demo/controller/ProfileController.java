package com.example.demo.controller;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.exception.CustomErrorCode;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.NotificationService;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {//个人资料控制器，做不完了，跳了 P28

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable(name = "id") String userId,
                          HttpServletRequest request,
                          Model model){
        List<Question> questions =questionMapper.findQuestionByUserId(userId);
        String remoteAddr = request.getRemoteAddr();
        model.addAttribute("questions",questions);
        return "profile";
    }

    @GetMapping("/profile/{id}/replies")
    public String replies(@PathVariable(name = "id") int userId,
                          HttpServletRequest request,
                          Model model){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            throw new CustomException(CustomErrorCode.NO_LOGIN);
        }
        if(!user.getId().toString().equals(String.valueOf(userId))){
            return "redirect:/profile/"+user.getId().toString();
        }
        List<NotificationDTO> noticeList=notificationService.getAllNotice(userId);
        model.addAttribute("noticeList",noticeList);
        return "replies";
    }

    @GetMapping("/profile/{id}/info")
    public String profile_info(@PathVariable(name = "id") String userId,
                          HttpServletRequest request,
                          Model model){

        String remoteAddr = request.getRemoteAddr();
        model.addAttribute("ip",remoteAddr);
        return "userInfo";
    }
}
