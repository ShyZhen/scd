package com.litblc.common.requestBean.test;

import com.litblc.common.requestBean.ValidatableBean;

/**
 * @Author zhenhuaixiu
 * @Date 2023/10/25 14:06
 * @Version 1.0
 */
public class TestRaw implements ValidatableBean {
    public long id;
    public long userId;
    public String nickname;
    public String avatar;

    @Override
    public void validator() {
        if (this.id <= 0) {
            throw new IllegalArgumentException("id参数非法");
        }

        if (this.userId <= 0) {
            throw new IllegalArgumentException("userId参数非法");
        }
    }
}
