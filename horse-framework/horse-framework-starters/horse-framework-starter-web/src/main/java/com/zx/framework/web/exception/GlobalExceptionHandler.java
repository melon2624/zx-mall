package com.zx.framework.web.exception;


import com.zx.framework.common.exception.BaseResultCodeEnum;
import com.zx.framework.common.exception.BizException;
import com.zx.framework.common.result.ResultWrapper;
import com.zx.framework.common.utils.SpringContextUtil;
import com.zx.framework.web.util.RequestContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zx.framework.common.exception.ExceptionOrderConstant.EXCEPTION_ORDER_LOW;

/**
 * 全局异常捕获以及错误日志管理
 * <p>
 * - 异常可以分为业务异常、程序异常，程序异常又分为前端传参异常和后端程序异常
 * - 只有程序异常需要打印error日志，配合日志采集实时跟踪程序异常
 * - 同时如果前端传参异常需要明确提示错误原因，方便甩锅[dog]
 *
 * @author liao, R
 */
@Slf4j
@ControllerAdvice
@Order(EXCEPTION_ORDER_LOW)
public class GlobalExceptionHandler {


    /**
     * 处理业务异常，BizException
     */
    @ResponseBody
    @ExceptionHandler(BizException.class)
    public ResultWrapper<Void> handleException(BizException bizException) {
        log.warn("业务异常 {}", bizException.getMessage());
        return ResultWrapper.fail(bizException.getCode(), bizException.getMsg());
    }


    /**
     * 参数绑定异常（包含接口参数类型不匹配、@Valid入参校验）
     * <p>
     * 如前端传string，后端用Long接收
     * 或者@Valid注解校验不通过
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    public ResultWrapper<Void> handleException(BindException e) {
        String errorMsg = e.getFieldErrors().stream()
                .map(error -> "[" + error.getField() + "]" + error.getDefaultMessage())
                .collect(Collectors.joining(","));
        log.error("接口入参异常，uri：{}，错误信息：{}", getRequestInfo(), errorMsg);
        return ResultWrapper.fail(BaseResultCodeEnum.ILLEGAL_ARGUMENT, errorMsg);
    }

    /**
     * 请求不可读异常
     * 常见入参数据错误导致json反序列化异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultWrapper<Void> handleException(HttpMessageNotReadableException e) {
        String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
        log.error("接口入参异常，uri：{}，错误信息：{}", getRequestInfo(), rootCauseMessage);
        return ResultWrapper.fail(BaseResultCodeEnum.ILLEGAL_ARGUMENT, rootCauseMessage);
    }

    /**
     * validate 参数校验错误异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultWrapper<Void> handleException(ConstraintViolationException e) {
        log.error("参数异常 {}", e.getMessage());
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<String> errorArr = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        String errMsg = String.join(" ", errorArr.toArray(new String[]{}));
        return ResultWrapper.fail(BaseResultCodeEnum.ILLEGAL_ARGUMENT, errMsg);
    }

    /**
     * 参数类型不匹配
     * 如需要Integer但传入String
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResultWrapper<Void> handleException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("接口入参类型异常，uri：{}，错误信息：{}", getRequestInfo(), "Parameter \"" + e.getName() + "\" " + e.getMessage());
        return ResultWrapper.fail(BaseResultCodeEnum.ILLEGAL_ARGUMENT, e.getMessage());
    }

    /**
     * 请求方式错误
     * 如post方式请求get接口
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultWrapper<Void> handleException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("接口请求方式错误，uri：{}，错误信息：{}", getRequestInfo(), e.getMessage());
        return ResultWrapper.fail(BaseResultCodeEnum.ILLEGAL_REQUEST, e.getMessage());
    }

    /**
     * 其他异常（即程序异常）
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultWrapper<Void> handleException(Exception exception) {
        // 打印堆栈信息
        log.error("系统异常，uri：{}", getRequestInfo(), exception);
        return ResultWrapper.fail(BaseResultCodeEnum.SYSTEM_ERROR, getExceptionMsgWithStack(exception));
    }

    /**
     * 输出堆栈信息返回前端，帮助排查问题
     */
    private String getExceptionMsgWithStack(Exception exception) {
        StringBuilder errorMsg = new StringBuilder();
        // 生产上不建议打印出来
        if (SpringContextUtil.isDev() || SpringContextUtil.isTest() || SpringContextUtil.isProd()) {
            errorMsg.append(exception.toString());
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                // 将堆栈信息中第一个业务代码的位置显示出来
                if (stackTraceElement.toString().contains("com.zx")) {
                    errorMsg.append(stackTraceElement);
                    break;
                }
            }
        }
        return errorMsg.toString();
    }

    /**
     * 请求信息，含uri和请求参数
     */
    private String getRequestInfo() {
        HttpServletRequest request = RequestContextUtils.getRequest();
        String queryString = request.getQueryString();
        return request.getMethod() + ":" + request.getRequestURI() + (queryString.isEmpty() ? "" : "?" + queryString);
    }
}
