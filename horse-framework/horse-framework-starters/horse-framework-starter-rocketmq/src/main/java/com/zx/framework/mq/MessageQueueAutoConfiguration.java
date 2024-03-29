package com.zx.framework.mq;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zx.framework.mq.annotation.MessageQueueScan;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author liao
 */
@Configuration
@MessageQueueScan("com.zx.**.mq.**")
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.zx.framework.mq.config")
@ComponentScan("com.zx.framework.mq.*")
public class MessageQueueAutoConfiguration {

    @Bean
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        timeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return timeModule;
    }

    /**
     * 覆盖原有的rocketmq 序列化
     */
    @Primary
    @Bean
    public RocketMQMessageConverter customRocketMessageConverter(JavaTimeModule javaTimeModule) {
        RocketMQMessageConverter rocketMessageConverter = new RocketMQMessageConverter();
        CompositeMessageConverter messageConverter = (CompositeMessageConverter) rocketMessageConverter.getMessageConverter();
        Optional<MessageConverter> messageConverterOptional = messageConverter.getConverters().stream()
                .filter(messageConverter1 -> messageConverter1.getClass()
                        .equals(MappingJackson2MessageConverter.class)).findAny();
        messageConverterOptional.ifPresent(messageConverter12 -> {
            MappingJackson2MessageConverter mappingFastJsonMessageConverter = (MappingJackson2MessageConverter) messageConverter12;
            ObjectMapper objectMapper = mappingFastJsonMessageConverter.getObjectMapper();
            objectMapper.registerModule(javaTimeModule);
        });
        return rocketMessageConverter;
    }

}
