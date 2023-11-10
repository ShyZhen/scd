package com.litblc.fmock.moduleA.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litblc.fmock.moduleA.entity.Posts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author zhenhuaixiu
 * @Date 2023/10/17 15:35
 * @Version 1.0
 */
@Mapper
public interface PostsMapper extends BaseMapper<Posts> {
    // 添加复杂的操作数据库方法，就可以在xml中写，跟mybatis用法一样
    List<Posts> getAll();

    @Select(value = "select count(id) from users")
    int getAllCount();

    @Select("select name from users where id = #{id}")
    String getUserName(long id);

    @Select("select * from posts where id > #{id}")
    List<Posts> getList(@Param("id") long id);
}
