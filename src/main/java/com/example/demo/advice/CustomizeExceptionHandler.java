package com.example.demo.advice;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.ResultDTO;
import com.example.demo.exception.CustomErrorCode;
import com.example.demo.exception.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(CustomException.class)
    ModelAndView handle(HttpServletRequest request, Throwable e , Model model,
                  HttpServletResponse response) {
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){//判断请求的是否是json格式
            //返回json
            ResultDTO resultDTO=null;
            if(e instanceof CustomException){
                resultDTO= (ResultDTO) ResultDTO.errorOf((CustomException)e);
            } else {
                resultDTO= ResultDTO.errorOf(CustomErrorCode.UNKNOWN_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ex) {
            }
            return null;
        }
        else {
            //返回错误页面
            if(e instanceof CustomException){
                model.addAttribute("message",e.getMessage());//乱码问题已解决
            } else {
                model.addAttribute("message",CustomErrorCode.UNKNOWN_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
