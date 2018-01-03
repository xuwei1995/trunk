package com.serviceindeed.yike.yikemo.domain.validated;

import org.springframework.validation.annotation.Validated;

import java.lang.annotation.Annotation;

public class StudentValidated implements Validated {
    @Override
    public Class<?>[] value() {
        return new Class[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
