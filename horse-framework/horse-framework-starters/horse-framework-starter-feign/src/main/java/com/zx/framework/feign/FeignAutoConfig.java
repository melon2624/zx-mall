package com.zx.framework.feign;

import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liao
 */
@Configuration
@EnableFeignClients(basePackages = "com.zx.**.feign")
public class FeignAutoConfig {

    @Bean
    public Decoder feignDecoder(ObjectProvider<HttpMessageConverters> messageConverters) {
        return new OptionalDecoder((new ResponseEntityDecoder(new FeignClientDecoder(new SpringDecoder(messageConverters)))));
    }


    @Bean
    public ErrorDecoder feignErrorDecoder(ObjectProvider<HttpMessageConverters> messageConverters) {
        return new FeignExceptionDecoder(new SpringDecoder(messageConverters));
    }
}
