package org.fsn.framework.core.controller;



import org.fsn.framework.common.exception.error.BaseBusinessModuleException;
import org.fsn.framework.common.exception.error.DefaultError;
import org.fsn.framework.common.pojo.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author benju.xie
 * @version 0.0.1
 * @datetime 2017/5/10 17:41
 */
@ControllerAdvice(annotations = RestController.class)
@Order(2)
public class GlobalExceptionRestController {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionRestController.class);
    @ExceptionHandler(value = {RuntimeException.class, BaseBusinessModuleException.class})
    @ResponseBody //在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
    public BaseResponse exceptionHandler(RuntimeException e, HttpServletResponse response) {
        BaseResponse resp = null;

        if (e instanceof BaseBusinessModuleException) {
            resp = new BaseResponse(((BaseBusinessModuleException) e).getError());
        } else {
            resp = new BaseResponse();
            resp.setStatus(BaseResponse.Status.FAILED);
            resp.setErrorCode(DefaultError.SYSTEM_INTERNAL_ERROR.getErrorCode());
            resp.setErrorMessage(e.getMessage());
            logger.error("System Exception:",e);
        }

        return resp;
    }

    // spring 参数 验证
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public BaseResponse bindExceptionHandler(BindException e, HttpServletResponse response){
        BaseResponse resp = new BaseResponse(DefaultError.ARGUMENT_ERROR);
        resp.setErrorMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return resp;
    }
}
