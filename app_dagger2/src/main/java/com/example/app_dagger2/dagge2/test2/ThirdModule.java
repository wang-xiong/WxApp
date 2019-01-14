package com.example.app_dagger2.dagge2.test2;

import dagger.Module;
import dagger.Provides;

@Module
public class ThirdModule {

    @Provides
    String test() {
        return "";
    }
}
