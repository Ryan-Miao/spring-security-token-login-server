package com.example.serverapi.config;

import com.example.serverapi.domain.common.vo.BaseResponse;
import com.example.serverapi.domain.security.exception.UnAuthorizedException;
import com.example.serverapi.log.SystemEvent;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理.
 *
 * @author miaoruifeng
 */
@Slf4j
@ResponseBody
@ControllerAdvice("com.example.serverapi.domain")
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {


    /**
     * Hibernate validate 失败的异常.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        final BindingResult bindingResult = ex.getBindingResult();
        String msg = getErrors(bindingResult).toString();
        SystemEvent event = SystemEvent.PARAM_INVALID_ERROR;
        return ResponseEntity.badRequest().body(
            new BaseResponse<>(event.getCode(),
                msg));
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
        HttpStatus status, WebRequest request) {

        SystemEvent event = SystemEvent.PARAM_INVALID_ERROR;
        return ResponseEntity.badRequest()
            .body(new BaseResponse<>(event.getCode(), ex.getMessage()));
    }

    /**
     * Json格式错误异常.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {

        String message = ex.getMessage();
        final Throwable cause = ex.getCause();
        if (cause != null) {
            message = message + "; Caused by:" + cause.getMessage();
        }
        SystemEvent event = SystemEvent.PARAM_INVALID_ERROR;
        return ResponseEntity.badRequest()
            .body(new BaseResponse<>(event.getCode(), message));
    }

    /**
     * 权限不足403.
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResponse<Object> permissionNotAllowedException(
        AccessDeniedException exception) {
        SystemEvent event = SystemEvent.PERMISSION_NOT_ALLOWD_ERROR;
        return new BaseResponse<>(event.getCode(), exception.getMessage());
    }

    /**
     * 认证失败401.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public BaseResponse<Object> unAuthorizedException(UnAuthorizedException exception) {
        SystemEvent event = SystemEvent.UN_AUTHORIZED_ERROR;
        return new BaseResponse<>(event.getCode(), exception.getMessage());
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(BizException.class)
//    public BaseResponse<Object> bizException(BizException exception) {
//        return new BaseResponse<>(exception.getCode(), exception.getMessage());
//    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse<Object> otherException(Exception exception) {
        SystemEvent event = SystemEvent.INTERNAL_SERVER_ERROR;
        return new BaseResponse<>(event.getCode(),
            exception.getMessage());
    }

    private Map<String, String> getErrors(BindingResult result) {

        return result.getFieldErrors().stream()
            .collect(Collectors.toMap(FieldError::getField,
                DefaultMessageSourceResolvable::getDefaultMessage,
                (o1, o2) -> o1));
    }
}
