package com.zx.framework.web;

import com.zx.framework.web.config.DateTimeConfig;
import com.zx.framework.web.config.DateTimeConvertPrimaryConfig;
import com.zx.framework.web.exception.GlobalExceptionHandler;
import com.zx.framework.web.exception.SentinelBlockExceptionHandler;
import com.zx.framework.web.result.InitializingAdviceDecorator;
import com.zx.framework.web.swagger.SwaggerConfiguration;
import com.zx.framework.web.swagger.SwaggerShortcutController;
import com.zx.framework.web.transform.TransformConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author liao
 */
@Configuration
@ComponentScan(basePackages = "com.zx.framework.web")
@Import({SwaggerConfiguration.class, InitializingAdviceDecorator.class,
        GlobalExceptionHandler.class, SentinelBlockExceptionHandler.class,
        DateTimeConfig.class, SwaggerShortcutController.class, TransformConfig.class, DateTimeConvertPrimaryConfig.class})
public class WebAutoConfig {

}
