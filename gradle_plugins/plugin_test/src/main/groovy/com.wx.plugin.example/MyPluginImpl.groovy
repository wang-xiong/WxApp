package com.wx.plugin.example

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


class MyPluginImpl implements Plugin<Project> {

    @Override
    void apply(Project project) {
        //添加监听
        project.gradle.addListener(new TimeListener())

        //参数传递
        project.extensions.create('pluginExt', PluginExtension)
        project.pluginExt.extensions.create('nestExt', PluginNestExtension)
        project.task(group: "wx",'customTask', type : CustomTask)

        //registerTransform
        def  android = project.extensions.getByType(AppExtension)
        android.registerTransform(new MyTransform(project))
    }
}