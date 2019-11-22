package com.lengjiye.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.lengjiye.base.application.MasterApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * SharedPreferences操作工具类
 * Created by lz on 2016/7/14.
 */
public class SPTool {

    /**
     * 默认使用包名作为name
     */
    private static String NAME = MasterApplication.getInstance().applicationContext().getPackageName();

    private SPTool() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        setParam(key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue) {
        return (String) getParam(key, defValue);
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void putString(String name, String key, String value) {
        setParam(name, key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String name, String key, String defValue) {
        return (String) getParam(name, key, defValue);
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String key, boolean value) {
        setParam(key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return (boolean) getParam(key, defValue);
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String name, String key, boolean value) {
        setParam(name, key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(String name, String key, boolean defValue) {
        return (boolean) getParam(name, key, defValue);
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void putInt(String key, int value) {
        setParam(key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(String key, int defValue) {
        return (int) getParam(key, defValue);
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void putInt(String name, String key, int value) {
        setParam(name, key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(String name, String key, int defValue) {
        return (int) getParam(name, key, defValue);
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void putLong(String key, long value) {
        setParam(key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(String key, long defValue) {
        return (long) getParam(key, defValue);
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void putLong(String name, String key, long value) {
        setParam(name, key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(String name, String key, long defValue) {
        return (long) getParam(name, key, defValue);
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void putFloat(String key, float value) {
        setParam(key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(String key, float defValue) {
        return (float) getParam(key, defValue);
    }

    /**
     * 将传入的key和value放入到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void putFloat(String name, String key, float value) {
        setParam(name, key, value);
    }

    /**
     * 根据传入的key和defValue从SharedPreferences中获取相应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(String name, String key, float defValue) {
        return (float) getParam(name, key, defValue);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    private static void setParam(String key, Object object) {
        setParam(NAME, key, object);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    private static Object getParam(String key, Object defaultObject) {
        return getParam(NAME, key, defaultObject);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    private static void setParam(String name, String key, Object object) {
        SharedPreferences sp = MasterApplication.getInstance().applicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    private static Object getParam(String name, String key, Object defaultObject) {
        SharedPreferences sp = MasterApplication.getInstance().applicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return contains(NAME, key);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String name, String key) {
        SharedPreferences sp = MasterApplication.getInstance().applicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        remove(NAME, key);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String name, String key) {
        SharedPreferences sp = MasterApplication.getInstance().applicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        clear(NAME);
    }

    /**
     * 清除所有数据
     */
    public static void clear(String name) {
        SharedPreferences sp = MasterApplication.getInstance().applicationContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}