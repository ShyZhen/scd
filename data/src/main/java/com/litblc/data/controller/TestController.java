package com.litblc.data.controller;

import com.litblc.common.exception.ServiceException;
import com.litblc.common.result.Result;
import com.litblc.data.entity.Posts;
import com.litblc.data.mapper.PostsMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @Autowired
    DataSource dataSource;

    @Autowired
    PostsMapper postsMapper;

    @GetMapping("/func1")
    public void func1() {
        System.out.println(dataSource);
    }

    @GetMapping("/func2")
    public List<Posts> func2() {
//        List<Posts> postList = this.postsMapper.getList(1);
        List<Posts> postList = this.postsMapper.getAll();
        return postList;
    }

    @Operation(summary = "更新，验证druid防火墙update-allow配置")
    @GetMapping("/func3")
    public void func3() {
        Posts newPosts = new Posts();
        newPosts.setId(4L);
        newPosts.setTitle("新标题444");

        this.postsMapper.updateById(newPosts);
    }

    @Operation(summary = "测试使用common模块result")
    @GetMapping("/func4")
    public Result<List<Posts>> func4() {
//        List<Posts> postList = this.postsMapper.getList(1);
        List<Posts> postList = this.postsMapper.getAll();
        return Result.successWithData(postList);
    }

    @Operation(summary = "测试使用common模块的exception自动捕获、手动抛出")
    @PostMapping("/func5")
    public Result<List<Posts>> func5() {
        List<Posts> postList = this.postsMapper.getAll();
        throw new ServiceException("手动抛出业务异常");
        //return Result.successWithData(postList);
    }
}
