package com.wx.plugin.example

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CustomTask extends DefaultTask {

    @TaskAction
    void output() {
        println("param1 is ${project.pluginExt.param1}")
        println "param2 is ${project.pluginExt.param2}"
        println "param3 is ${project.pluginExt.param3}"
        println "nestparam1 is ${project.pluginExt.nestExt.nestParam1}"
        println "nestparam2 is ${project.pluginExt.nestExt.nestParam2}"
        println "nestparam3 is ${project.pluginExt.nestExt.nestParam3}"
    }
}