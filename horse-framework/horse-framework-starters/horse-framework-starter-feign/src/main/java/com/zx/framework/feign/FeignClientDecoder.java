package com.zx.framework.feign;

import com.zx.framework.common.result.ResultWrapper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author liao
 */
@Slf4j
public class FeignClientDecoder implements Decoder {
    private final SpringDecoder decoder;

    public FeignClientDecoder(SpringDecoder decoder) {
        this.decoder = decoder;
    }

    /**
     * @param response
     * @param type
     * @return
     * @throws IOException
     * @throws FeignException
     */
    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        ParameterizedTypeImpl resultWrapperType = ParameterizedTypeImpl.make(ResultWrapper.class, new Type[]{type}, null);
        ResultWrapper<?> resultWrapper = (ResultWrapper<?>) this.decoder.decode(response, resultWrapperType);
        log.info("feign 响应结果 {}", resultWrapper);
        return resultWrapper.getData();
    }
}
