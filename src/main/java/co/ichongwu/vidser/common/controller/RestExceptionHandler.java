package co.ichongwu.vidser.common.controller;

import co.ichongwu.vidser.common.config.ErrorCode;
import co.ichongwu.vidser.common.exception.CommonException;
import co.ichongwu.vidser.common.exception.ValidationException;
import co.ichongwu.vidser.common.vo.CommonResponse;
import co.ichongwu.vidser.common.vo.ErJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;

/**
 * 异常处理器
 * Created by whf on 3/24/16.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    private CommonResponse handleException(HttpServletRequest req, Throwable ex) {
        log.warn("got exception => {}", ex);

        // 判断是否为自定义异常
        if (ex instanceof CommonException) {

            // 是表单验证错误
            if (ex instanceof ValidationException) {
                ValidationException validationEx = (ValidationException) ex;
                CommonResponse foxResponse = CommonResponse.getValidationErrorResp(Arrays.asList(
                        validationEx.getErr()
                ));

                return foxResponse;

            }

//            // 远程调用失败
//            if (ex instanceof RemoteServiceException) {
//                return new CommonResponse(ErrorCode.RPC_ERROR);
//
//            }
//
//            if (ex instanceof SsoidExistException) {
//                return new CommonResponse(ErrorCode.SSOID_EXISTS);
//            }
//
//            // 包解析错误
//            if (ex instanceof PackageParseException) {
//                return new CommonResponse(ErrorCode.PKG_PARSE_ERROR);
//            }

        }


        return CommonResponse.getFailedResp(ex.getMessage());
    }

    /**
     * 处理@RequestParam错误, 即参数不足
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("请求参数缺失: {}", ex.getMessage());

        return new ResponseEntity<>(new CommonResponse(ErrorCode.ARG_INVALID), status);
    }

    /**
     * 处理500错误
     * @param ex
     * @param body
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // 请求方式不支持
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return new ResponseEntity<>(new CommonResponse(ErrorCode.REQUEST_METHOD_UNSUPPORTED), status);
        }

        log.error("got internal error : {}", ex);

        return new ResponseEntity<>(new CommonResponse(ErrorCode.INTERNAL_ERROR), status);
    }


    /**
     * 处理参数类型转换失败
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("参数类型不匹配");

        return new ResponseEntity<>(new CommonResponse(ErrorCode.ARG_INVALID), status);
    }

}
