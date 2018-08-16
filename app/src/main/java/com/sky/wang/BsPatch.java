package com.sky.wang;

/**
 * Created by bluesky on 2018/7/27.
 */

public class BsPatch {
    static {
        System.loadLibrary("bsdiff");
    }
    public static native int bspatch(String oldApk, String newApk, String patch);

}
