package co.ichongwu.vidser.common.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.ichongwu.vidser.common.config.ErrorCode;
import co.ichongwu.vidser.common.vo.CommonResponse;
import co.ichongwu.vidser.config.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ExceptionHandler没有处理的错误在这里处理
 * Created by whf on 4/1/16.
 */
@RestController
public class FinalExceptionHandler implements ErrorController {

    @RequestMapping(value = "/error", produces = Constants.MIME.MINE_JSON)
    public CommonResponse error(HttpServletResponse resp, HttpServletRequest req) {
        // 处理404错误
        if (resp.getStatus() == HttpStatus.NOT_FOUND.value()) {
            return new CommonResponse(ErrorCode.URL_NOT_FOUND);
        }

        // POST请求,但URL不存在
        if (resp.getStatus() == HttpStatus.METHOD_NOT_ALLOWED.value() && req.getMethod().equals("POST")) {
            return new CommonResponse(ErrorCode.URL_NOT_FOUND);
        }

        // 500
        return new CommonResponse(ErrorCode.INTERNAL_ERROR);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
