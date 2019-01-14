package com.example.app_dagger2.dagge2.test1;



import dagger.Component;

@ActivityScope
@Component(modules = CommonModule.class, dependencies = SecondComponent.class)
public interface CommonComponent {
    void inject(Test1Activity activity);
}
