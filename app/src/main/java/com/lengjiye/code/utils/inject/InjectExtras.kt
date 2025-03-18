package com.lengjiye.code.utils.inject

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class InjectExtras(val value: String)
