package co.ichongwu.vidser.common.config;

/**
 * 错误信息
 * Created by whf on 3/20/16.
 */
public enum ErrorCode {
    SUCCESS(0, 0, "操作成功"),
    FAILED(1, 1, "操作失败"),

    INTERNAL_ERROR(10001, 1, "系统出错"),
    REQUEST_METHOD_UNSUPPORTED(10003, 1, "请求方式不支持"),
    RPC_ERROR(10004, 1, "系统RPC调用出错"),

    NO_PERMISSION(20002, 1, "权限不足"),
    NOT_LOGGED_IN(20003, 4, "未登陆"),
    NOT_REGISTERED_SSP(20004, 4, "ssp未注册"),
    SID_EXPIRED(20005, 4, "登陆过期"),
    USER_INFO_NOT_FOUND(20006, 1, "未查询到用户信息"),

    ARG_INVALID(30002, 2, "请求参数缺失"),
    NOT_FOUND(30005, 1, "资源不存在"),
    URL_NOT_FOUND(30006, 1, "URL错误"),
    SSOID_EXISTS(30007, 1, "ssoid重复"),
    PKG_PARSE_ERROR(30008, 2, "包解析失败"),

    USER_NOT_FOUND(2, 1, "user not found");

    //TEST(4, "test");


    /**
     * 错误代码
     */
    private int code;

    private int globalCode;

    /**
     * 简短描述
     */
    private String message;

    private ErrorCode(int code, int globalCode, String msg) {
        this.code = code;
        this.globalCode = globalCode;
        this.message = msg;
    }

    public int getGlobalCode() {
        return this.globalCode;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
