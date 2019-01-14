package com.example.app_dagger2.dagge2.test1;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}


/**
 * 自定义注解ActivityScope作用是限定被它标记的对象生命周期与对应的Activity相同
 * @Scope：标记局部单例；
 * @Retention ：指定了它是运行时注解,就是说注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在.
 */
