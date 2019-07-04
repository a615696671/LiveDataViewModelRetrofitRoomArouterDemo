package com.example.common.bassdiff;

public class BassdiffUtils {
static {
    System.loadLibrary("bassdiff-lib");
}

    public static native int diff(String oldpath, String newpath, String patch);



    public static native int patch(String oldpath, String newpath, String patch);
}
