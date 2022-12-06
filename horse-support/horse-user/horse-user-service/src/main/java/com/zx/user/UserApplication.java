package com.zx.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangxin
 * @date 2022/12/5 14:33
 */
@Configuration
@SpringBootApplication(scanBasePackages = "com.zx.user")
public class UserApplication {



    public static void main(String[] args) {

        SpringApplication.run(UserApplication.class, args);

    }

}