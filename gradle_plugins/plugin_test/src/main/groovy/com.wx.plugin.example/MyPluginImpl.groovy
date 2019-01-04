package com.wx.plugin.example

import org.gradle.api.Plugin
import org.gradle.api.Project


class MyPluginImpl implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.gradle.addListener(new TimeListener())

        project.extensions.create('pluginExt', PluginExtension)
        //1.可以在project.extensions继续添加
        //project.extensions.create('pluginExt', PluginExtension)
        //也可以在新增的project.pluginExt.extensions添加
        project.pluginExt.extensions.create('nestExt', PluginNestExtension)

        project.task(group: "wx",'customTask', type : CustomTask)
    }
}