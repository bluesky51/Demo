package com.sky.wang.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bluesky on 2018/7/23.
 * SharedPreferences操作工具包<br>
 * <p>
 * <b>说明</b> 本工具包只能在单进程项目下使用，多进程共享请使用如下demo的两行代码重写: <br>
 * Context otherContext = c.createPackageContext( "com.android.contacts",
 * Context.CONTEXT_IGNORE_SECURITY); <br>
 * SharedPreferences sp = otherContext.getSharedPreferences( "my_file",
 * Context.MODE_MULTI_PROCESS);</br>
 *
 */

public class SharedPreferenceHelper {
    /**
     * 写入int 值
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @param v        value
     */
    public static void write(Context context, String fileName, String k, int v) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(k, v);
        editor.apply();
    }

    /**
     * 写入boolean值
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @param v        value
     */
    public static void write(Context context, String fileName, String k,
                             boolean v) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(k, v);
        editor.apply();
    }

    /**
     * 写入String数据
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @param v        String value
     */
    public static void write(Context context, String fileName, String k,
                             String v) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(k, v);
        editor.apply();
    }

    /**
     * 写入String数据
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @param v        String value
     */
    public static void write(Context context, String fileName, String k,
                             long v) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putLong(k, v);
        editor.apply();
    }

    /**
     * 读取int 值
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @return result
     */
    public static int readInt(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getInt(k, 0);
    }

    /**
     * 读取long 值
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @return result
     */
    public static long readLong(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getLong(k, 0L);
    }

    /**
     * 读取int 值
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @param defv     默认值
     * @return result
     */
    public static int readInt(Context context, String fileName, String k,
                              int defv) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getInt(k, defv);
    }

    /**
     * 读取boolean值
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @return result
     */
    public static boolean readBoolean(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getBoolean(k, false);
    }

    /**
     * 读取boolean值
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @param defBool  默认值
     * @return result
     */
    public static boolean readBoolean(Context context, String fileName,
                                      String k, boolean defBool) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getBoolean(k, defBool);
    }

    /**
     * 读取String 值
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @return result
     */
    public static String readString(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getString(k, null);
    }

    /**
     * 读取String 值
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key
     * @param defV     默认值
     * @return 结果
     */
    public static String readString(Context context, String fileName, String k,
                                    String defV) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.getString(k, defV);
    }

    /**
     * 移除一个数据
     *
     * @param context  上下文环境
     * @param fileName sp name
     * @param k        key值
     */
    public static void remove(Context context, String fileName, String k) {
        SharedPreferences preference = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(k);
        editor.apply();
    }

    /**
     * 清理sp文件
     *
     * @param cxt      上下文环境
     * @param fileName sp name
     */
    public static void clean(Context cxt, String fileName) {
        SharedPreferences preference = cxt.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 判断当前是否存在key
     *
     * @param cxt      上下文环境
     * @param fileName sp name
     * @param key      key
     * @return boolean
     */
    public static boolean containKey(Context cxt, String fileName, String key) {
        SharedPreferences preference = cxt.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        return preference.contains(key);
    }

}
