#include <jni.h>
#include <string>

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
