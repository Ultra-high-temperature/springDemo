package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    //User表内，用户ID唯一标记一个用户
}
//{
//private String account_id;
//private String login_name;
//private String token;
//private Long gmt_create;
//private Long gmt_modified;
//        }