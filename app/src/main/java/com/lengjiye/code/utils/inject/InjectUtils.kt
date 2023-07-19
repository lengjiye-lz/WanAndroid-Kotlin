package com.lengjiye.code.utils.inject

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.lengjiye.tools.log.log
import java.lang.reflect.Field
import java.util.Arrays

object InjectUtils {

    fun injectExtras(activity: AppCompatActivity) {
        val cls = activity.javaClass
        val declaredFields = cls.declaredFields
        declaredFields.forEach {
            if (it.isAnnotationPresent(InjectExtras::class.java)) {
                val injectExtras = it.getAnnotation(InjectExtras::class.java)
                val value = it.type.simpleName
                val name = injectExtras.value
                it.isAccessible = true
                getClassType(value, name, activity, it)
                log("value:$value  name:$name ")
            }
        }
    }

    /**
     * 笨办法
     */
    private fun getClassType(
        type: String?,
        name: String,
        activity: AppCompatActivity,
        field: Field
    ) {
        val value = when (type) {
            "String" -> {
                activity.intent.getStringExtra(name)
            }
            "Integer" -> {
                activity.intent.getIntExtra(name, 0)
            }
            "Boolean" -> {
                activity.intent.getBooleanExtra(name, false)
            }
            "Float" -> {
                activity.intent.getFloatExtra(name, 0f)
            }
            "Double" -> {
                activity.intent.getDoubleExtra(name, 0.0)
            }
            "Long" -> {
                activity.intent.getLongExtra(name, 0L)
            }
            "Byte" -> {
                activity.intent.getByteExtra(name, 0)
            }
            "Short" -> {
                activity.intent.getShortExtra(name, 0)
            }
            "Char" -> {
                activity.intent.getCharExtra(name, 0.toChar())
            }
            else -> {
                log("type:$type")
                null
            }
        }
        if (value != null) {
            // 反射赋值
            field.set(activity, value)
        }
    }


    /**
     * 反射加注解
     */
    fun injectExtras1(activity: AppCompatActivity) {
        val cls = activity.javaClass
        val declaredFields = cls.declaredFields
        val extras = activity.intent.extras
        declaredFields.forEach { field ->
            if (field.isAnnotationPresent(InjectExtras::class.java)) {
                val injectExtras = field.getAnnotation(InjectExtras::class.java)
                val name = injectExtras.value.ifEmpty { field.name } // 获取key 如果value没有值 则根据字段获得
                if (extras?.containsKey(name) == true) {
                    var value = extras.get(name)
                    val type = field.type.componentType
                    // 处理Parcelable数组类型
                    if (field.type.isArray && Parcelable::class.java.isAssignableFrom(type)) {
                        val objs = value as Array<Any>
                        val objects =
                            Arrays.copyOf(objs, objs.size, field.type as Class<out Array<Any>>)
                        value = objects
                    }
                    field.isAccessible = true // 设置访问权限
                    // 第一个参数：在哪个对象上设置属性/调用方法 ，如果对象是static就可以传null
                    field.set(activity, value)
                }
            }
        }
    }
}