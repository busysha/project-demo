package com.project.common.common;

/**
 * Created by dell on 2016/9/21.
 */
public interface ConsoleConstants {
    interface System{
        String NOT_HANDLE = "未处理";
        String DEFAULT_NAME = "默认名";
    }



    enum MessageKey{
        SUCCESS("1","success"),
        FAIL("0","fail")
        ;
        private String code;
        private String message;
        MessageKey(String code, String description) {
            this.code = code;
            this.message = description;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
