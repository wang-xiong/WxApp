package com.example.app_dagger2.dagge2.test1;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonModule {

    private ICommonView iView;

    public CommonModule(ICommonView iView) {
        this.iView = iView;
    }

    @Provides
    @ActivityScope
    public ICommonView provideICommonView() {
        return  this.iView;
    }
}
