package com.litblc.fmock.moduleA.crontab;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author zhenhuaixiu
 * @Date 2023/11/10 17:03
 * @Version 1.0
 */

// @Scheduled 参数可以接受两种定时的设置，一种是我们常用的`cron="*/6 * * * * ?"`,一种是 fixedRate = 6000，两种都表示每隔六秒跑一次。
// @Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
// @Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
// @Scheduled(initialDelay=1000, fixedRate=6000) ：第一次延迟1秒后执行，之后按 fixedRate 的规则每6秒执行一次
// cron參考 https://blog.csdn.net/Linweiqiang5/article/details/86741258

@EnableScheduling
@Component
public class SchedulerTask {

    private int count = 0;

    @Scheduled(cron = "*/6 * * * * ?")
    private void task1() {
        System.out.println("这样就执行定时任务,第几次：" + (++this.count));
    }

    @Scheduled(fixedRate = 6000)
    public void task2() {
        System.out.println("现在时间：" + (new Date()));
    }
}
