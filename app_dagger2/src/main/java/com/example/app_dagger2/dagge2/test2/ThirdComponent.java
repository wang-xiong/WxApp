package com.example.app_dagger2.dagge2.test2;

import dagger.Subcomponent;

@Subcomponent(modules = ThirdModule.class)
public interface ThirdComponent {
    void inject(Test2Activity activity);
}
