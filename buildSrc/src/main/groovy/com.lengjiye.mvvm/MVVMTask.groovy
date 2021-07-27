package com.lengjiye.mvvm

import groovy.xml.Namespace
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.text.SimpleDateFormat

class MVVMTask extends DefaultTask {

    @TaskAction
    def generateMVVMFile() {
        def mvvmExtension = project.extensions.getByType(MVVMExtension)
        // 应用ID
        def applicationId = mvvmExtension.applicationId
        // 模块名 驼峰命名
        def functionName = mvvmExtension.functionName
        // 作者
        def author = mvvmExtension.author
        // 1是activity 2是fragment 0是activity和fragment
        int addViewType = mvvmExtension.addViewType

        getapplication()
        def mvpArray = [
                [
                        templateName: "MVVMBean.template",
                        type        : "bean",
                        fileName    : "Bean.kt"
                ],
                [
                        templateName: "MVVMModel.template",
                        type        : "model",
                        fileName    : "Model.kt"
                ],
                [
                        templateName: "MVVMViewModel.template",
                        type        : "viewmodel",
                        fileName    : "ViewModel.kt"
                ],
                [
                        templateName: "MVVMServe.template",
                        type        : "serve",
                        fileName    : "Serve.kt"
                ],
                [
                        templateName: "MVVMLayout.template",
                        type        : "laoyout",
                        fileName    : ".xml"
                ]
        ]

        if (addViewType != 2) {
            mvpArray.add([
                    templateName: "MVVMActivity.template",
                    type        : "activity",
                    fileName    : "Activity.kt"
            ])
        }

        if (addViewType != 1) {
            mvpArray.add([
                    templateName: "MVVMFragment.template",
                    type        : "fragment",
                    fileName    : "Fragment.kt"
            ])
        }

        String dateString = getFormatTime()

        def mBinding = [
                applicaitionId: applicationId,
                functionName  : functionName,
                packageName   : getToLowerCase(functionName),
                layoutName    : getToLowerCase(functionName),
                date          : dateString,
                author        : author
        ]

        def packageFilePath = mvvmExtension.applicationId.replace(".", "/")

        //代码文件根路径
        def fullPath = project.projectDir.toString() + "/src/main/java/" + packageFilePath

        generateMvpFile(mvpArray, mBinding, fullPath, addViewType)

    }

    void generateMvpFile(def mvpArray, def binding, def fullPath, int addViewType) {

        for (int i = 0; i < mvpArray.size(); i++) {
            preGenerateFile(mvpArray[i], binding, fullPath, addViewType)
        }
    }

    void preGenerateFile(def map, def binding, def fullPath, int addViewType) {
        def template = makeTemplate(map.templateName, binding)
        def path
        def fileName
        if ("laoyout" == map.type) {
            path = project.projectDir.toString() + "/src/main/res/layout/"
            if (addViewType != 2) {
                fileName = path + "/activity_" + binding.layoutName + map.fileName
            }
            if (addViewType != 1) {
                fileName = path + "/fragment_" + binding.layoutName + map.fileName
            }
        } else {
            path = fullPath + "/" + binding.packageName + "/" + map.type
            fileName = path + "/" + binding.functionName + map.fileName
        }
        generateFile(path, fileName, template)
    }

    /**
     * 加载模板
     */
    def makeTemplate(def templateName, def binding) {
        File f = new File("./buildSrc/template/" + templateName)
        def engine = new groovy.text.GStringTemplateEngine()
        return engine.createTemplate(f).make(binding)
    }

    /**
     * 生成文件
     * @param path
     * @param fileName
     * @param template
     */
    void generateFile(def path, def fileName, def template) {
        //验证文件路径，没有则创建
        validatePath(path)

        File mvpFile = new File(fileName)

        //如果文件已经存在，直接返回
        if (!mvpFile.exists()) {
            mvpFile.createNewFile()
        } else {
            return
        }

        FileOutputStream out = new FileOutputStream(mvpFile, false)
        out.write(template.toString().getBytes("utf-8"))
        out.close()
    }

    /**
     * 验证文件路径，没有则创建
     * @param path
     */
    void validatePath(def path) {
        File mvpFileDir = new File(path)

        if (!mvpFileDir.exists()) {
            mvpFileDir.mkdirs()
        }
    }

    def getToLowerCase(def fileName) {
        return fileName.toLowerCase()
    }

    /**
     * 格式化当前时间
     * @return
     */
    def getFormatTime() {
        Date date = new Date()

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(date)
    }

    def getapplication(){
        // 声明命名空间
        def android = new Namespace('http://schemas.android.com/apk/res/android', 'android')
        // 获取apk application name
        def parser = new XmlParser()

        def testManifest = parser.parse(project.projectDir.toString() + "/src/main/AndroidManifest.xml")
        println "lz:" + testManifest.application[0].attribute(android.name)
    }
}