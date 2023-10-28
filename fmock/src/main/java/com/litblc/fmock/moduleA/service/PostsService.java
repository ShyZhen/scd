package com.litblc.fmock.moduleA.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.litblc.fmock.moduleA.entity.Posts;
import com.litblc.fmock.moduleA.mapper.PostsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhenhuaixiu
 * @Date 2023/10/17 15:37
 * @Version 1.0
 */
@Service
public class PostsService {
    private final PostsMapper postsMapper;

    public PostsService(PostsMapper postsMapper) {
        this.postsMapper = postsMapper;
    }

    // 快捷orm方法
    public Posts copyPosts(Posts posts, long id) {
        Posts originPosts = this.postsMapper.selectById(id);
        String defaultTitle = "默认title";
        if (originPosts != null) {
            defaultTitle = originPosts.getTitle();
        }

        posts.setTitle(defaultTitle);
        postsMapper.insert(posts);
        return posts;
    }

    // wrapper
    public List<Posts> wrapperPostsList() {
        QueryWrapper<Posts> queryWrapper = new QueryWrapper<>();
        // 查询id != 2的
        queryWrapper.ne("id", 2).select("id, title");

        return postsMapper.selectList(queryWrapper);
    }

    // xml方式
    public List<Posts> getAllPosts() {
        return postsMapper.getAll();
    }

}
