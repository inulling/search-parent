package net.vipmro.search.core.model;

import java.io.Serializable;

import static net.vipmro.search.core.constants.ResultConstant.CODE_SUCCESS;


/**
 * 返回结果
 *
 * @param <T>
 * @author fengxiangyang
 * @date 2018/11/30
 */
public class Result<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return CODE_SUCCESS == code;
    }
}
