package com.xxxxxx.web;

import com.lergo.framework.annotation.UnAuthentication;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

public class DemoLoginBase {

    //https://www.cnblogs.com/jin-zhe/p/8241368.html
    @ModelAttribute("userInfo")
    @UnAuthentication
    public User userInfo(@RequestHeader(value = "token", required = false) String token) {
        //从token中获取用户信息
        return new User(1, token);
    }


    @Data
    @AllArgsConstructor
    static class User {

        private Integer id;
        private String name;

    }
}
