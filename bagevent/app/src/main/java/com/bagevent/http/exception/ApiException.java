package com.bagevent.http.exception;

/**
 * =============================================
 * <p>
 * author lshsh
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2018/11/2
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ApiException extends Exception {
    private int code;
    private String error; //错误

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(int code,String error){
        this.code = code;
        this.error = error;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "--ApiException--" + "\n" +
                "code: " + code + "\n" +
                "error: " + error;
    }
}
