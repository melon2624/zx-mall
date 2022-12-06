package com.zx.framework.web.safety.repeat;

import java.lang.annotation.*;

/**
 * @author liao
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface RepeatRequestIntercept {
}
