package com.litblc.fmock.controller;

import com.litblc.fmock.entity.Posts;
import com.litblc.fmock.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author zhenhuaixiu
 * @Date 2023/10/17 15:41
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/fmock")
public class PostsController {

    @Autowired
    private PostsService postsService;

    public PostsController() {
        System.out.println("test");
    }

    @GetMapping(value = "/copy/{id}")
    @Operation(summary = "根据ID复制一篇文章insert入库")
    public Posts copyPosts(
            @PathVariable(value = "id") long id
    ) {
        Posts posts = new Posts().setTitle("scd-title")
                .setPoster("https://xxx.com/aa.jpg")
                .setContent("{\"key\":\"val\"}")
                .setSummary("summary")
                .setUserId(15L)
                .setUuid("uuid-xx");

        return this.postsService.copyPosts(posts, id);
    }

    @GetMapping(value = "list")
    @Operation(summary = "获取文章列表")
    public List<Posts> postsList() {
        return this.postsService.wrapperPostsList();
    }

    @GetMapping(value = "listDesc")
    @Operation(summary = "获取文章列表")
    public List<Posts> postsListDesc() {
        return this.postsService.getAllPosts();
    }
}
