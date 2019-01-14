package com.example.app_dagger2.dagge2.test2;

import dagger.Component;

@Component(modules = MyParentModule.class)
public interface MyParentComponent {
    ThirdComponent provideSubcomponent();
}
