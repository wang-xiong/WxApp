package com.example.lib_wx_annotation;


import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Provide {
    Class<? extends Annotation>[] annotations() default {};

    Class<? extends Annotation>[] typeAnnotations() default {};
}
