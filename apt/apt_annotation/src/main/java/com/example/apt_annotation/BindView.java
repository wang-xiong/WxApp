package com.example.apt_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 1.元注解：用来定义定义注解的注解，共用四种原注解：@Retention, @Target, @Inherited, @Documented
 * 2.@Retention：保留的范围：默认CLASS,
 * SOURCE：只在源码中可用
 * CLASS：在源码和字节码中可用
 * RUNTIME：在源码，字节码，运行时都可用
 * 3.@Retention：用来修饰哪些程序元素
 * TYPE, METHOD, CONSTRUCTOR, FIELD, PARAMETER等，未标注则表示可修饰所有
 * 4.@Inherited 是否可以被继承，默认为false
 * 5.@Documented 是否会保存到 Javadoc 文档中
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface BindView {
    int value();
}