package com.lengjiye.code.utils.inject

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class InjectExtras(val value: String)
