package com.leis.common.log.annotation;

import com.leis.common.log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    String title();

    OperatorType operatorType() default OperatorType.MANAGE;

    int businessType();

    boolean isSaveRequestData() default true;

    boolean isSaveResponseData() default true;
}
