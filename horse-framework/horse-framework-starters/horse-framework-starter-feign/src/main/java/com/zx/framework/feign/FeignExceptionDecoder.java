package com.zx.framework.feign;

import com.zx.framework.common.exception.BizException;
import com.zx.framework.common.result.ResultWrapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.IOException;

/**
 * @author liao
 */
@Slf4j
public class FeignExceptionDecoder extends ErrorDecoder.Default {

    private final SpringDecoder decoder;

    public FeignExceptionDecoder(SpringDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            ResultWrapper<?> result = (ResultWrapper<?>) decoder.decode(response, ResultWrapper.class);
            log.error("feign 请求结果 {}", result);
            return new BizException(result.getCode(), result.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
