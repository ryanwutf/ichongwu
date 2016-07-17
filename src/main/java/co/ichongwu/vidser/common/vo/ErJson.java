package co.ichongwu.vidser.common.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * {
            "status":1,
            "statusInfo":{
                  "global": "the_golbal_error",
                  "field": {
                        "field1": "this_field_error",
                        "field2": "that_field_error"
                  }
             },
             "data":{}
         }
 *
 */
public class ErJson {

    public static final int STATUS_SUCCESS = 0;
    
    public static final int STATUS_SYSTEM_ERROR = 1;
    
    public static final int STATUS_BUSINESS_ERROR = 2;
    
    public static final int STATUS_NO_AUTH = 126;
    
    public static final int STATUS_NO_LOGIN = 127; 
     
    private int status = STATUS_SUCCESS;

    private StatusInfo statusInfo;

    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public StatusInfo getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(StatusInfo statusInfo) {
        this.statusInfo = statusInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void systemError() {
        status = STATUS_SYSTEM_ERROR;
    }

    public void businessError() {
        status = STATUS_BUSINESS_ERROR;
    }

    public ErJson noAuth() {
        status = STATUS_NO_AUTH;
        setGlobalError("没有权限！");
        return this;
    }

    public ErJson noLogin() {
        status = STATUS_NO_LOGIN;
        setGlobalError("没有登录！");
        return this;
    }

    public void setGlobalError(String error) {
        if (statusInfo == null) {
            statusInfo = new StatusInfo();
        }
        statusInfo.setGlobal(error);
    }

    public void addFieldError(String field, String error) {
        if (statusInfo == null) {
            statusInfo = new StatusInfo();
        }
        statusInfo.addFieldError(field, error);
    }

    public static ErJson success(Object data) {
        ErJson json = new ErJson();
        json.setData(data);
        return json;
    }
    
    public static ErJson failure(String message) {
        ErJson json = new ErJson();
        json.businessError();
        json.setGlobalError(message);
        return json;
    }

    public static class StatusInfo {

        private String global;

        private Map<String, String> field;

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

        public void addFieldError(String fieldName, String error) {
            if (field == null) {
                field = new HashMap<String, String>();
            }
            field.put(fieldName, error);
        }

    }

}
