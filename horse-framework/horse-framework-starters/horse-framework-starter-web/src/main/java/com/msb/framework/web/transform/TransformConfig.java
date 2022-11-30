package com.zx.framework.web.transform;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zx.framework.common.result.ResultWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

/**
 * 转换器配置类
 *
 * @author R
 */
@Configuration
public class TransformConfig {

    @Bean
    public PageConvert<Object> pageConvert() {
        return new PageConvert<>();
    }

    @Bean
    public ResultWrapperToListConvert resultWrapperToListConvert() {
        return new ResultWrapperToListConvert();
    }

    /**
     * IPage -> List
     */
    public static class PageConvert<T> implements Converter<IPage<T>, List<T>> {

        @Override
        public List<T> convert(IPage<T> source) {
            return source.getRecords();
        }
    }

    /**
     * ResultWrapper -> List | Bean
     */
    public static class ResultWrapperToListConvert implements Converter<ResultWrapper<?>, Object> {

        @Override
        public Object convert(ResultWrapper<?> object) {
            Object data = object.getData();
            if (data instanceof IPage) {
                return ((IPage<?>) data).getRecords();
            }
            // List或bean直接返回
            return data;

        }
    }


}
