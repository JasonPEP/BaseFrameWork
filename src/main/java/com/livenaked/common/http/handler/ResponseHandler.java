package com.livenaked.common.http.handler;

import com.livenaked.common.Constants;
import com.livenaked.common.ResponseCode;
import com.livenaked.common.http.Response;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 响应控制器
 */
@Order(1)
@RestControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        if (methodParameter.getMethod().isAnnotationPresent(Constants.IGNORE_MARK_ANNOTATION)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!(body instanceof Response)) {
            Response res = new Response(ResponseCode.SUCCESS);
            res.setData(body);
            return res;
        }
        //清空MDC
        MDC.clear();
        return body;
    }


}
