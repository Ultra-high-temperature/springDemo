package com.example.demo.mapper;

import com.example.demo.model.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("INSERT INTO notification" +
            "(notifier,receiver, outerId, type, gmt_create, status) VALUES " +
            "(#{notifier},#{receiver}, #{outerId}, #{type}, #{gmt_create}, #{status})")
    void insert(Notification notification);

    @Select("SELECT * FROM notification WHERE receiver=#{userId}")
    List<Notification> findAllNoticeByReceiverId(int userId);

    @Select("SELECT * FROM notification WHERE id=#{noticeId}")
    Notification findNoticeByNoticeId(Long noticeId);

    @Update("UPDATE notification SET status = 1 WHERE id = #{noticeId}")
    void UpdataReadInfoById(Long noticeId);

//    private long id;
//    private long notifier;
//    private long receiver;
//    private long outerId;
//    private int type;
//    private long gmt_create;
//    private long status;
}
