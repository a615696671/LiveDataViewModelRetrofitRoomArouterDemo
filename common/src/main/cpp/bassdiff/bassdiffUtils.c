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
JNIEXPORT jint JNICALL
Java_com_example_common_bassdiff_BassdiffUtils_diff(JNIEnv *env, jclass type, jstring oldpath_,
                                        jstring newpath_, jstring patch_) {
    char *argv[4];
    argv[0] = "bsdiff";
//    argv[1] = (*env)->GetStringUTFChars(env, oldpath_, 0);
//    argv[2] = (*env)->GetStringUTFChars(env, newpath_, 0);
//    argv[3] = (*env)->GetStringUTFChars(env, patch_, 0);

    strcpy(argv[1], (*env)->GetStringUTFChars(env, oldpath_, 0));
    strcpy(argv[2], (*env)->GetStringUTFChars(env, newpath_, 0));
    strcpy(argv[3], (*env)->GetStringUTFChars(env, patch_, 0));

    bspatch_main(4, argv);
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
//    argv[1] =(*env)->GetStringUTFChars(env, oldpath_, 0);
//    argv[2] = (*env)->GetStringUTFChars(env, newpath_, 0);
//    argv[3] = (*env)->GetStringUTFChars(env, patch_, 0);
    strcpy(argv[1], (*env)->GetStringUTFChars(env, oldpath_, 0));
    strcpy(argv[2], (*env)->GetStringUTFChars(env, newpath_, 0));
    strcpy(argv[3], (*env)->GetStringUTFChars(env, patch_, 0));

    bspatch_main(4, argv);
    (*env)->ReleaseStringUTFChars(env, oldpath_, argv[1]);
    (*env)->ReleaseStringUTFChars(env, newpath_, argv[2]);
    (*env)->ReleaseStringUTFChars(env, patch_, argv[3]);
    free(argv);
    return 0;

}