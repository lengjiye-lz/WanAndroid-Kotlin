package com.lengjiye.base.utils;


import androidx.lifecycle.AndroidViewModel;

import com.lengjiye.base.viewmodel.EmptyViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 获取泛型ViewModel的class对象
 */
public class ClassUtil {
    /**
     * 获取泛型ViewModel的class对象
     */
    public static <T> Class<T> getViewModel(Object obj) {
        Class<?> currentClass = obj.getClass();
        Class<T> tClass = getGenericClass(currentClass);
        if (tClass == null || tClass == AndroidViewModel.class || tClass == EmptyViewModel.class) {
            return null;
        }
        return tClass;
    }

    private static <T> Class<T> getGenericClass(Class<?> mClass) {
        Type type = mClass.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        // 获取范型集合
        Type[] types = parameterizedType.getActualTypeArguments();
        for (Type t : types) {
            Class<T> tClass = (Class<T>) t;
            if (AndroidViewModel.class.isAssignableFrom(tClass)) {
                return tClass;
            }
        }
        return null;
    }
}
