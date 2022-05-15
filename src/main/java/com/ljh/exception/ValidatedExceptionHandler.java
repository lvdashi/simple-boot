package com.ljh.exception;

import com.ljh.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 参数验证
 *
 * @author jinhuilv
 * @date 2022/04/26 16:38
 **/
@Slf4j
@ControllerAdvice
public class ValidatedExceptionHandler {

    /**
     * 参数验证统一返回
     * @param bindException
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    public ApiResponse validationException(BindException bindException){
        List<ObjectError> errors =  bindException.getAllErrors();
        if(!CollectionUtils.isEmpty(errors)){
            StringBuilder sb = new StringBuilder();
            errors.forEach(e->sb.append(e.getDefaultMessage()).append(","));
            return ApiResponse.error(sb.toString());
        }
        return ApiResponse.error(bindException.getLocalizedMessage());
    }

    /**
     * 参数验证统一返回
     * @param exception
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ApiResponse constraintViolationException(ConstraintViolationException exception){
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        if(!CollectionUtils.isEmpty(constraintViolations)){
            StringBuilder sb = new StringBuilder();
            constraintViolations.forEach(e->sb.append(e.getMessage()).append(","));
            return ApiResponse.error(sb.toString());
        }
        return ApiResponse.error(exception.getLocalizedMessage());
    }
}
