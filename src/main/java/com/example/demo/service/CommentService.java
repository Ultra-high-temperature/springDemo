package com.example.demo.service;

import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.exception.CustomErrorCode;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Transactional//标于类前时, 标示类中所有方法都进行事物处理
    //默认是有事务加入事务，没有就新建事务
    public void insert(Comment comment) {
        if (comment.getParent_id() == null || comment.getParent_id() == 0) {
            throw new CustomException(CustomErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (Integer.valueOf(comment.getType()) == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomException(CustomErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment db_comment=commentMapper.findByParentId(comment.getParent_id());
            if(db_comment==null){
                throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }
        else {
            //回复问题
            Question db_question = questionMapper.getById(Math.toIntExact(comment.getParent_id()));
            if(db_question==null){
                throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateComment(db_question.getId());
        }
    }
}
