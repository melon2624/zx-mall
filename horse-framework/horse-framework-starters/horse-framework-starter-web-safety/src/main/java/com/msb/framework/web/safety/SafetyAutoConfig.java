package com.zx.framework.web.safety;

import com.zx.framework.web.safety.repeat.RepeatRequestAspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author liao
 */
@Configuration
@Import(RepeatRequestAspect.class)
public class SafetyAutoConfig {

}
