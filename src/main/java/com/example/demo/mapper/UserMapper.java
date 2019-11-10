package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user ( account_id,login_name,token,gmt_create,gmt_modified ) " +
            "VALUES " +
            "( #{account_id},#{login_name},#{token},#{gmt_create},#{gmt_modified} );")
    void insert(User user);

    @Select("SELECT * FROM user WHERE token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findByID(@Param("id") int id);

    @Select("SELECT * FROM user WHERE account_id = #{account_id}")
    User findByAccount_id(@Param("account_id") String account_id);

    @Update("UPDATE user SET login_name=#{login_name}, gmt_modified=#{gmt_modified} ,token=#{token}" +
            "WHERE account_id=#{account_id}")
    void update(User user);

    //modified 1572921683147

    //User表内，用户ID唯一标记一个用户
}
//{
//private String account_id;
//private String login_name;
//private String token;
//private Long gmt_create;
//private Long gmt_modified;
//        }