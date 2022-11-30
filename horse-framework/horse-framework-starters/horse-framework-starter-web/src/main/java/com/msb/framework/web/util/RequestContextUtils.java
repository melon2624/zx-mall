package com.zx.framework.web.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author liao
 */
public class RequestContextUtils {

    private RequestContextUtils() {
    }

    public static HttpServletRequest getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes).getRequest())
                .orElse(null);
    }

    public static Optional<HttpServletRequest> getOptionalRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes).getRequest());
    }

    public static String getRequestUri() {
        return getOptionalRequest().map(HttpServletRequest::getRequestURI).orElse("-");
    }

    public static String getHeader(String header) {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getHeader(header);
    }
}
