#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_marvel_managers_NativeFunctions_getPublicKey(JNIEnv *env, jobject thiz) {
    jstring publicKey = env->NewStringUTF("78fd67285c16c429b9bb7106c40f5cb1");
    return publicKey;
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_marvel_managers_NativeFunctions_getPrivateKey(JNIEnv *env, jobject thiz) {
    jstring privateKey = env->NewStringUTF("3ae76dbafae7439165bdeac1e9b86f3d2d935f83");
    return privateKey;
}