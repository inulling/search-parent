package net.vipmro.search.core.exception;

import static net.vipmro.search.core.model.ResultConstant.CODE_EXCEPTION;

/**
 * Http自定义异常
 * @author fengxiangyang
 * @date 2018/11/30
 */
public class HttpException extends RuntimeException {
    private Integer code;
    private String msg;

    public HttpException() {
        super();
    }

    public HttpException(String message) {
        super(message);
        msg = message;
        this.code = CODE_EXCEPTION;
    }

    public HttpException(int retCd, String msgDes) {
        this.code = retCd;
        this.msg = msgDes;
    }

    public HttpException(String msgDes, Throwable e) {
        super(msgDes, e);
        this.msg = msgDes;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
