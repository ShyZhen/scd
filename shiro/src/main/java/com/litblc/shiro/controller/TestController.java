package com.litblc.shiro.controller;

import com.litblc.shiro.common.Result.Result;
import com.litblc.shiro.controller.base.BaseController;
import com.litblc.shiro.enums.Weekday;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController extends BaseController {

    @GetMapping("/func1/{day}")
    public Result<?> getEnum(@PathVariable int day) {
        if (day == Weekday.THU.getDayValue()) {
            System.out.println("周四");
            System.out.println(Weekday.THU.getDayValue());
        }

        List<String> list = new ArrayList();
        list.add("11");
        list.add("22");
        list.add("33");
        list.add("11");
        //list.remove("11");
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println(new String("12") == new String("12"));

        String[] arr = new String[3];
        arr[0] = "111";
        arr[1] = "222";
        arr[2] = "333";

        for (String s1: arr) {
            System.out.println(s1);
        }

        String name = Weekday.MON.name();
        return Result.successWithMsg(name);
    }

    @GetMapping("/func2")
    public Result<?> getEnum() {

        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 1001);
        map.put("pear", 1002);
        map.put("wtml", 1003);
        map.put("apple", 1000);  // 会覆盖相同key的值

        // System.out.println(map.keySet());

        // 迭代key
        for (String key: map.keySet()) {
            System.out.println("key:"+key+":"+map.get(key));
        }

        // 迭代对象entry
        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() +":" + entry.getValue());
        }

        return Result.successWithMsg("OK");
    }

    @GetMapping("/func3")
    public Result<?> getSet() {
        Set set = new HashSet();
        set.add("aa");
        set.add("bb");
        set.add("aa");  // 返回false,不会报错,经常用set去重重复元素
        set.add("cc");

        System.out.println(set);
        return Result.successWithData(set);
    }

    @GetMapping("/func4")
    public void queueDemo() {
        Queue<String> queue = new PriorityQueue();
        queue.add("aa");
        queue.add("cc");
        queue.add("dd");
        queue.offer("bb");  // 一般用offer比add添加队列更好，源码这么说的：因为当添加失败时，add只能抛异常，而offer返回false或者null
        queue.offer("ee");

        String s = queue.peek();  // peek() 取队首元素但不删除;  而element()获取失败会抛出异常
        System.out.println(s);

        String s2 = queue.poll();  // poll()方法来取出队首元素，当获取失败时，它不会抛异常，而是返回null;  而remove()获取失败会抛出异常
        System.out.println(s2);

        System.out.println(queue);
    }


    /**
     * 实现有序队列
     */
    @GetMapping("/func5")
    public void queueDemo2() {
        Queue<UserScore> queue = new PriorityQueue<>(new UserScoreCompare());
        queue.offer(new UserScore("用户1", "C", 1));
        queue.offer(new UserScore("用户2", "B", 2));
        queue.offer(new UserScore("用户3", "A",4));
        queue.offer(new UserScore("用户4", "E",2));
        queue.offer(new UserScore("用户5", "B",1));
        queue.offer(new UserScore("用户6", "V",1));
        queue.offer(new UserScore("用户7", "V",3));

        System.out.println(queue);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

    }
}


// 实现比较器
class UserScoreCompare implements Comparator<UserScore> {

    @Override
    public int compare(UserScore o1, UserScore o2) {
        // 处理V的优先级
        if (o1.type.charAt(0) == 'V' && o2.type.charAt(0) != 'V') {
            return -1;  // o1在前
        } else if (o1.type.charAt(0) != 'V' && o2.type.charAt(0) == 'V') {
            return 1;  // o2在前
        } else if (o1.type.charAt(0) == 'V') {
            // 两者都是V,按照index升序
            return o1.index.compareTo(o2.index);
        } else {
            // 都不是V,先按照type字典序比较,如果相同再按照index升序
            if (o1.type.charAt(0) != o2.type.charAt(0)) {
                return o1.type.compareTo(o2.type);
            } else {
                return o1.index.compareTo(o2.index);
            }

        }
    }
}

class UserScore {
    public final String name;
    public final String type;
    public final Integer index;

    public UserScore(String name, String type, Integer index) {
        this.name = name;
        this.type = type;
        this.index = index;
    }

    public String toString() {
        return name + "/" + type + "/" + index;
    }
}
