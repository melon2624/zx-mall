package com.zx.mall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangxin
 * @date 2022/12/20 16:15
 */
@SpringBootApplication(scanBasePackages = "com.zx.mall")
public class ProductServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProductServiceApplication.class,args);
    }

}
