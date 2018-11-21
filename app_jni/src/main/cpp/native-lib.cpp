#include <jni.h>
#include <string>
#include "stdlib.h"
#include "string.h"
#include <android/log.h>

#define TAG "wx-jni"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型

//首先将a强制声明为指向整数的指针，读取指针对应的整数
int compare(const void *a, const void *b) {
    return (*(int *) a - *(int *) b);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_app_1jni_MainActivity_stringFromJNI(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_app_1jni_MainActivity_updateTextFromC(
        JNIEnv *env,
        jobject instance) {

    //jobject 代表谁调用的这个方法（mainActivity这个类的对象）首先获取jclass
    jclass _jclass = env->GetObjectClass(instance);

    //(jclass clazz, const char* name, const char* sig)
    jfieldID _jfieldID = env->GetFieldID(_jclass, "name", "Ljava/lang/String;");

    //(jobject obj, jfieldID fieldID)
    jstring _jstring = static_cast<jstring>(env->GetObjectField(instance, _jfieldID));

    printf("%#x\n", _jstring);

    //转换成java String
    char *str = const_cast<char *>(env->GetStringUTFChars(_jstring, NULL));

    char text[20] = "success";

    char *result = strcat(str, text);

    // TODO


    return env->NewStringUTF(result);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_app_1jni_MainActivity_getMethod(JNIEnv *env, jobject instance) {
    jclass _jclass = env->GetObjectClass(instance);

    jmethodID _jmethodID = env->GetMethodID(_jclass, "getName", "()Ljava/lang/String;");

    jstring _jstring = static_cast<jstring>(env->CallObjectMethod(instance, _jmethodID));

    char *str = const_cast<char *>(env->GetStringUTFChars(_jstring, NULL));

    char text[20] = "success";

    char *result = strcat(str, text);

    return env->NewStringUTF(result);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_app_1jni_MainActivity_getArray(JNIEnv *env, jobject instance, jintArray array_) {
    jint *array = env->GetIntArrayElements(array_, NULL);

    int len = env->GetArrayLength(array_);

    //(void* __base, size_t __nmemb, size_t __size, int (*__comparator)(const void* __lhs, const void* __rhs))
    qsort(array, len, sizeof(int), compare);

    env->ReleaseIntArrayElements(array_, array, 0);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_app_1jni_MainActivity_getLocalReference(JNIEnv *env, jobject instance) {
    //模拟循环
    for (int i = 0; i < 100; i++) {
        jclass _jclass = env->FindClass("java/util/Date");

        jobject _jobject = env->NewObject(_jclass, env->GetMethodID(_jclass, "<init>", "()V"));
        //...对象的操作
        //局部的引用，通知gc回收
        env->DeleteLocalRef(_jobject);
        //全局的引用，通知gc回收
        //env->DeleteGlobalRef()
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_app_1jni_MainActivity_exception(JNIEnv *env, jobject instance) {
    //构造异常
    jclass _jclass = env->GetObjectClass(instance);
    jfieldID _jfieldID = env->GetFieldID(_jclass, "name2", "Ljava/lang/String;");

    //检查异常
    jthrowable _jthrowable = env->ExceptionOccurred();
    if (_jthrowable != NULL) {
        //为了保证java代码能继续执行，需要清除异常
        env->ExceptionClear();

        _jfieldID = env->GetFieldID(_jclass, "name", "Ljava/lang/String;");
    }

    jstring _jstring = static_cast<jstring>(env->GetObjectField(instance, _jfieldID));

    char *str = const_cast<char *>(env->GetStringUTFChars(_jstring, NULL));

    //构造异常
    if (strcmp(str, "test2") != 0) {
        //抛出异常Java的异常
        jclass newThrow = env->FindClass("java/lang/IllegalArgumentException");
        env->ThrowNew(newThrow, "c++ 参数错误");
    }

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_app_1jni_MainActivity_cached(JNIEnv *env, jobject instance) {
    jclass _jclass = env->GetObjectClass(instance);

    //局部静态变量 一种方法
    static jfieldID _jfieldID = NULL;
    if (_jfieldID == NULL) {
        _jfieldID = env->GetFieldID(_jclass, "name", "Ljava/lang/String;");
        LOGE("---------GetFieldID-----------");
        //printf("............");
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_app_1jni_MainActivity_init(JNIEnv *env, jclass _jclass) {
    //成员变量的初始化

    static jfieldID _jfieldID = NULL;
    if (_jfieldID == NULL) {
        _jfieldID = env->GetFieldID(_jclass, "name", "Ljava/lang/String;");
        LOGE("---------GetFieldID-----------\n");
        //printf("............");
    }
}