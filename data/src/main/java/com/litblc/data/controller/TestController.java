package com.litblc.data.controller;

import com.litblc.data.entity.Posts;
import com.litblc.data.mapper.PostsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
