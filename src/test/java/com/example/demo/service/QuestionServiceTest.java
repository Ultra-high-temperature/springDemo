package com.example.demo.service;

import com.example.demo.dto.PaginationDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    QuestionService questionService;

    @Test
    public void list() {
        PaginationDTO paginationDTO = questionService.list("search",0, 6);
    }
}