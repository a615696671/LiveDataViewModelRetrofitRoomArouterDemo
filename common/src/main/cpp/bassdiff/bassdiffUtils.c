#include <android/log.h>
#include "bassdiffUtils.h"
#include "bassdiff/bsdiff.c"
#include "bassdiff/bspatch.c"
#include "bzip2/bzlib.c"
#include "bzip2/crctable.c"
#include "bzip2/compress.c"
#include "bzip2/decompress.c"
#include "bzip2/randtable.c"
#include "bzip2/blocksort.c"
#include "bzip2/huffman.c"

#define LOG_TAG  "C_TAG"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
JNIEXPORT jint JNICALL
Java_com_example_common_bassdiff_BassdiffUtils_diff(JNIEnv *env, jclass type, jstring oldpath_,
                                        jstring newpath_, jstring patch_) {
    char *argv[4];
    argv[0] = "bsdiff";
    LOGD("开始copy地址");
    argv[1] = (*env)->GetStringUTFChars(env, oldpath_, 0);
    argv[2] = (*env)->GetStringUTFChars(env, newpath_, 0);
    argv[3] = (*env)->GetStringUTFChars(env, patch_, 0);

//    strcpy(argv[1], (*env)->GetStringUTFChars(env, oldpath_, 0));
//    strcpy(argv[2], (*env)->GetStringUTFChars(env, newpath_, 0));
//    strcpy(argv[3], (*env)->GetStringUTFChars(env, patch_, 0));
    LOGD("地址copy结束");
    bassdiff_main(4, argv);
    LOGD("差分完成");
    (*env)->ReleaseStringUTFChars(env, oldpath_, argv[1]);
    (*env)->ReleaseStringUTFChars(env, newpath_, argv[2]);
    (*env)->ReleaseStringUTFChars(env, patch_, argv[3]);
    free(argv);
    return 0;
}

JNIEXPORT jint JNICALL
Java_com_example_common_bassdiff_BassdiffUtils_patch(JNIEnv *env, jclass type, jstring oldpath_,
                                         jstring newpath_, jstring patch_) {
     char *argv[4];
    argv[0] = "bspatch";
    LOGD("开始copy地址");
    argv[1] =(*env)->GetStringUTFChars(env, oldpath_, 0);
    argv[2] = (*env)->GetStringUTFChars(env, newpath_, 0);
    argv[3] = (*env)->GetStringUTFChars(env, patch_, 0);
    LOGD("地址copy结束");
    bspatch_main(4, argv);
    LOGD("合并完成");
    (*env)->ReleaseStringUTFChars(env, oldpath_, argv[1]);
    (*env)->ReleaseStringUTFChars(env, newpath_, argv[2]);
    (*env)->ReleaseStringUTFChars(env, patch_, argv[3]);
    free(argv);
    return 0;

}