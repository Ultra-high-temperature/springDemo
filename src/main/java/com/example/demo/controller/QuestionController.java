package com.example.demo.controller;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){

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
