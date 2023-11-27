package com.xxxxxx.web;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo/account")
@Slf4j
@Tag(name = "登录样例", description = "统一获取用户信息")
public class DemoLoginController extends DemoLoginBase {

    @GetMapping(value = "testAtUser")
    public String test(@ModelAttribute("userInfo") @Parameter(hidden = true) User user) {
        return "my name is: " + user;
    }

}

