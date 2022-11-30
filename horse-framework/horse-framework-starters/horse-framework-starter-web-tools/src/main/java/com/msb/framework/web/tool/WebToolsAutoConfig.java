package com.zx.framework.web.tool;

import com.zx.framework.web.tool.excel.ExportExcelAspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author liao
 */
@Configuration
@Import(ExportExcelAspect.class)
public class WebToolsAutoConfig {
}
