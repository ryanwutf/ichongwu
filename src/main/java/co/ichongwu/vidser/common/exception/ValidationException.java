package co.ichongwu.vidser.common.exception;


import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.runners.model.FrameworkMember;

import co.ichongwu.vidser.common.vo.CommonResponse;


/**
 * Created by whf on 3/24/16.
 */
public class ValidationException extends CommonException {
    
    private CommonResponse.ValidationError err;

    public ValidationException() {}

    public ValidationException(String argName, String msg) {
        super("表单参数验证错误");
        err = new CommonResponse.ValidationError(argName, msg);
    }

    public CommonResponse.ValidationError getErr() {
        return err;
    }
}
