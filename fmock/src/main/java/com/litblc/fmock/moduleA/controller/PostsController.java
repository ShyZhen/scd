package com.litblc.fmock.moduleA.controller;

import com.litblc.fmock.moduleA.config.RedisTemplateConfig;
import com.litblc.fmock.moduleA.entity.Posts;
import com.litblc.fmock.moduleA.mapper.PostsMapper;
import com.litblc.fmock.moduleA.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author zhenhuaixiu
 * @Date 2023/10/17 15:41
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/fmock")
public class PostsController {

    @Autowired
    private PostsMapper postsMapper;

    @Autowired
    private PostsService postsService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;  // RedisTemplateConfig

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
    @Cacheable(value = "posts")
    public List<Posts> postsListDesc() {
        System.out.println("再次访问这个接口，这句话不会输出，证明走了缓存");

        List<Posts> res = this.postsService.getAllPosts();
        this.redisTemplate.opsForValue().set("posts1", res, 60L, TimeUnit.SECONDS);  // 手动存储的是字符串
        this.redisTemplate.opsForValue().set("posts2", res);  // 永久期限，正常json格式

        return res;
    }

    @GetMapping(value = "mb")
    @Operation(summary = "mybatis原生用法测试")
    public void getList() {
        int userCount = this.postsMapper.getAllCount();
        String userName = this.postsMapper.getUserName(4);
        List<Posts> res = this.postsMapper.getList(4);

        System.out.println(userCount);
        System.out.println(userName);
        System.out.println(res);
    }
}
