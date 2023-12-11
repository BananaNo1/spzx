package com.leis.exception;

import com.leis.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private Integer code;
    private String message;


    private ResultCodeEnum resultCodeEnum;

    public CustomException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public CustomException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
