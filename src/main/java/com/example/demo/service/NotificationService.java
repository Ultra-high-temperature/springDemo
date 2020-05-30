package com.example.demo.service;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.exception.CustomErrorCode;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Notification;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public List<NotificationDTO> getAllNotice(int receiver) {
        List<Notification> allNotice = notificationMapper.findAllNoticeByReceiverId(receiver);
//        获取所有的通知消息 通知消息只涵盖发送人id、接收人id、通知id、上一级评论/问题id
        List<NotificationDTO> noticeList = new ArrayList<>();
//        dto对象补充了所在的帖子id、帖子标题、接收人等内容
        NotificationDTO tool = new NotificationDTO();
        for (Notification notification : allNotice) {
            tool.setGmt_create(notification.getGmt_create());
            tool.setStatus(notification.getStatus());
            tool.setType(notification.getType());
            tool.setId(notification.getId());
            long user = notification.getNotifier();
            User notifier = userMapper.findByID((int) user);
//            找到对应的接收人
            long outerId = notification.getOuterId();
            Question outerQuestion = null;
            String outTitle = null;
            int questionId;
            if (notification.getType() == 1) {//当为一级评论时
                outerQuestion = questionMapper.getById((int) outerId);
                outTitle = outerQuestion.getTitle();
                questionId=outerQuestion.getId();

            } else if (notification.getType() == 2) {//当为二级评论时
                Comment comment = commentMapper.findCommentById(outerId);
                int parent_id = Math.toIntExact(comment.getParent_id());
                outerQuestion = questionMapper.getById(parent_id);
                outTitle = outerQuestion.getTitle();
                questionId=outerQuestion.getId();
            } else {//正常情况下不会进来
                throw new CustomException(CustomErrorCode.UNKNOWN_ERROR);
            }
            tool.setOuterId(questionId);
            tool.setOuterTitle(outTitle);
            tool.setNotifier(notifier);
            noticeList.add(tool);
            tool = new NotificationDTO();
        }
        return noticeList;
    }

    public void isRead(long noticeId) {
        Notification noticeByNoticeId = notificationMapper.findNoticeByNoticeId(noticeId);
        if(noticeByNoticeId!=null){
            notificationMapper.UpdataReadInfoById(noticeId);
        }
    }
}
