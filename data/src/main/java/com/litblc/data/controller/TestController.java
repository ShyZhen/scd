package com.litblc.data.controller;

import com.litblc.common.exception.ServiceException;
import com.litblc.common.result.Result;
import com.litblc.common.utils.CustomHashUtils;
import com.litblc.common.utils.CustomTimeUtils;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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

    @Operation(summary = "测试使用common模块的utils类，格式化时间")
    @GetMapping("/timeTest")
    public void timeTest() {
        System.out.println("时间戳: " + System.currentTimeMillis());
        System.out.println("无时区LocalDateTime: " + LocalDateTime.now());

        ZonedDateTime zbj = ZonedDateTime.now();                              // 默认时区             2025-05-12T15:28:06.182897200+08:00[Asia/Shanghai]
        ZonedDateTime zny = ZonedDateTime.now(ZoneId.of("America/New_York")); // 用指定时区获取当前时间  2025-05-12T03:28:06.182897200-04:00[America/New_York]
        System.out.println(zbj);
        System.out.println(zny);

        // 格式化 DateTimeFormatter
        DateTimeFormatter formatter0 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm ZZZZ");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("E, yyyy-MMMM-dd HH:mm", Locale.US);
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy MMM dd EE HH:mm", Locale.CHINA);
        System.out.println(zbj.format(formatter0));
        System.out.println(zbj.format(formatter1));
        System.out.println(zbj.format(formatter2));
        System.out.println(zbj.format(formatter3));


        // 数据库中存储long时间戳，通过自定义timestampToString方法转换
        System.out.println("timestampToString1:"+ CustomTimeUtils.timestampToString(System.currentTimeMillis()));
        System.out.println("timestampToString2:"+ CustomTimeUtils.timestampToString(System.currentTimeMillis(), Locale.US, "America/New_York"));
    }

    @GetMapping("/func6")
    public void func6() {
        System.out.println(CustomHashUtils.md5WithSalt("password123"));  // e4860a33a1a93e149627d789c9a38a9ef0ea30f3
        System.out.println(CustomHashUtils.verifySaltedMd5("password123", "e4860a33a1a93e149627d789c9a38a9ef0ea30f3"));
    }


}
