package com.litblc.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litblc.shiro.dto.response.UserResponseDto;
import com.litblc.shiro.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<Users> {

    @Select(value = "select * from users where name = #{username}")
    Users findByUsername(String username);

    @Select(value = "select * from users where id = #{id}")
    UserResponseDto findByIdNew(Long id);

    @Select(value = "select count(id) from users where name = #{username}")
    Boolean existsByUsername(String username);
}
