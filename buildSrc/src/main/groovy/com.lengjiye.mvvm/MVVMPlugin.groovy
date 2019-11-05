package com.lengjiye.mvvm

import org.gradle.api.Plugin
import org.gradle.api.Project

class MVVMPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("mvvm", MVVMExtension)
        project.task('generateMVVM', type: MVVMTask){
            group "mvvmGenerator"
        }
    }
}
