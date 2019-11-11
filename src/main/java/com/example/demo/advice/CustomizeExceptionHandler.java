package com.example.demo.advice;

import com.example.demo.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e , Model model) {

        if(e instanceof CustomException){
            model.addAttribute("message","e.getMessage()");
            //还是乱码问题，战术挠头
        }
        else {
            model.addAttribute("message","不知道什么异常");
        }
        return new ModelAndView("error");
    }


}
