package co.ichongwu.vidser.common.vo;

import co.ichongwu.vidser.common.config.ErrorCode;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by whf on 3/20/16.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {
    /**
     * 默认为成功的状态码
     */
    private int code = ErrorCode.SUCCESS.getCode();
    private MsgObj message = MsgObj.successObj;

    /**
     * 数据字段
     */
    private Object result;



    private transient static CommonResponse successResp = new CommonResponse(ErrorCode.SUCCESS);
    private transient static CommonResponse failedResp = new CommonResponse(ErrorCode.FAILED);

    public CommonResponse() {}

    /**
     * 带额外数据的返回
     * @param data
     */
    public CommonResponse(Object data) {
        this.result = data;
    }
    
    public static CommonResponse success(Object data) {
    	return new CommonResponse(data);
    }

    /**
     * 用错误信息构造对象
     * @param errorCode
     */
    public CommonResponse(ErrorCode errorCode) {
        code = errorCode.getGlobalCode();
        message = new MsgObj(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 请求参数错误
     * @param errList
     */
    public CommonResponse(List<ObjectError> errList) {
        int SIZE = errList.size();
        Map<String, String> fieldErrMap = new HashedMap(SIZE + SIZE / 2);

        errList.forEach(err -> {
            FieldError fieldError = (FieldError) err;
            fieldErrMap.put(fieldError.getField(), err.getDefaultMessage());

        });

        this.code = ErrorCode.ARG_INVALID.getGlobalCode();
        this.message = new MsgObj(fieldErrMap, ErrorCode.ARG_INVALID);
    }

    /**
     * 返回状态为成功,不带数据的单例对象
     * @return
     */
    public static CommonResponse getSuccessfulResponse() {
        return CommonResponse.successResp;
    }

    /**
     * 返回状态为失败, 不带数据的单例对象
     * @return
     */
    public static CommonResponse getFailedResp() {
        return CommonResponse.failedResp;
    }

    public static CommonResponse getFailedResp(String msg) {
        CommonResponse response = new CommonResponse(msg);
        response.setCode(ErrorCode.ARG_INVALID.getCode());
        response.message = new MsgObj(ErrorCode.ARG_INVALID.getCode(), ErrorCode.ARG_INVALID.getMessage());

        return response;
    }

    /**
     * 得到参数验证错误的返回
     * @param errList
     * @return
     */
    public static CommonResponse getValidationErrorResp(List<ValidationError> errList) {
        CommonResponse resp = new CommonResponse();
        resp.setCode(ErrorCode.ARG_INVALID.getGlobalCode());
        resp.message = new MsgObj(errList);

        return resp;
    }

    /**
     * 得到重定向的返回
     * @param addr
     * @return
     */
    public static CommonResponse getRedirectResp(String addr) {
        CommonResponse fox = new CommonResponse(ErrorCode.NOT_LOGGED_IN);
        fox.message = new MsgObj(addr);

        return fox;
    }

    public int getCode() {
        return code;
    }


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    protected void setCode(int status) {
        this.code = status;
    }


    /**
     * 封闭参数验证错误信息
     */
    public static class ValidationError {
        /**
         * 参数名
         */
        private String argName;
        /**
         * 对应的错误信息
         */
        private String message;

        public ValidationError(String argName, String message) {
            this.argName = argName;
            this.message = message;
        }

        public String getArgName() {
            return argName;
        }

        public void setArgName(String argName) {
            this.argName = argName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * 对应message字段. 适配前端数据格式
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MsgObj {
        private String redirect;
        private String noSession;
        private String global;

        private Map<String, String> field;

        private int code;

        /**
         * 操作成功情况下应该使用的对象
         */
        public static MsgObj successObj = new MsgObj(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage());

        public MsgObj(List<ValidationError> errList) {
            this(ErrorCode.ARG_INVALID.getGlobalCode(), ErrorCode.ARG_INVALID.getMessage());

            // list转换为map
            this.field = errList.stream()
                    .collect(Collectors.toMap( err -> err.getArgName(), err -> err.getMessage() ));

        }

        /**
         * 参数错误
         * @param field
         * @param errorCode
         */
        public MsgObj(Map<String, String> field, ErrorCode errorCode) {
            this(errorCode.getCode(), errorCode.getMessage());

            this.field = field;
        }

        /**
         * 使用错误码和错误信息构造对象
         * @param code
         * @param msg
         */
        public MsgObj(int code, String msg) {
            this.code = code;
            this.global = msg;
        }

        /**
         * 使用重定向地址构造对象
         * @param redirect
         */
        public MsgObj(String redirect) {
            this.code = ErrorCode.NOT_LOGGED_IN.getCode();
            //this.noSession = ErrorCode.NOT_LOGGED_IN.getMessage();
            this.redirect = redirect;
        }




        public String getRedirect() {
            return redirect;
        }

        public void setRedirect(String redirect) {
            this.redirect = redirect;
        }

        public String getNoSession() {
            return noSession;
        }

        public void setNoSession(String noSession) {
            this.noSession = noSession;
        }

        public String getGlobal() {
            return global;
        }

        public void setGlobal(String global) {
            this.global = global;
        }

        public Map<String, String> getField() {
            return field;
        }

        public void setField(Map<String, String> field) {
            this.field = field;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public MsgObj getMessage() {
        return message;
    }

    public void setMessage(MsgObj message) {
        this.message = message;
    }
}
