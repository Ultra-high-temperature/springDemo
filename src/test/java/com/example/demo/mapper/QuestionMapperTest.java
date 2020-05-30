package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionMapperTest {

    @Autowired
    private QuestionMapper questionMapper;

    @Test
    public void findAboutQuestion() {
        List<QuestionDTO> java = questionMapper.findAboutQuestion("Java");
        System.out.println(java);
    }

    @Test
    public void list() {
        questionMapper.list2("一|二",1,5);
    }
}