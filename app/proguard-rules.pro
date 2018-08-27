# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#代表注释
#-代表一条规则的开始
#keep: 保留
#dont：不要，dontwarn：表示不要提示警告
#ignore 忽略，例如ignorewarning：表示忽略警告
# 不优化
#-dontoptimize
# 代码循环优化次数，0-7，默认为5
#-optimizationpasses 5
# 不做预校验
#-dontpreverify

#-keep class coinread.info.** 表示本包及子包下的类名都不混淆，但是都会混淆具体的方法名和变量
#-keep class coinread.info.*  表示本包下的类名不混淆，但子包下的类名都混淆，但是都会混淆具体的方法名和变量
#-keep class coinread.info.* {*;} 表示都保持不被混淆
#-keep class coinread.info.One {   表示此类中的某个部分不混淆
#         public <methods>;
#    }
# -keepclassmembers 不保留包名 防止成员被移除或者被重命名
# -keepclasseswithmembers 保留类名和成员名

#1、基本规则，保留四大组件，自定义view不被混淆，因为这些子类都可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}
#2、反射，反射用到的类一般需要保留，否则会出现问题。实体类不被混淆
-keep class coinread.info.Bean.** { *; }
#3：枚举不能被混淆
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
#4：继承的保留
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

#5：jni  方法不可混淆
-keepclasseswithmembernames class * {
native <methods>;
}
#6:资源文件不被混淆
-keep class **.R$* {
*;
}
-keepclassmembers class **.R$* {
public static <fields>;
}
#7：webview的一些处理
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
public void *(android.webkit.WebView, jav.lang.String);
}
#在app中与HTML5的JavaScript的交互进行特殊处理 我们需要确保这些js要调用的原生方法不能够被混淆，于是我们需要做如下处理：
-keepclassmembers class com.ljd.example.JSInterface {
<methods>;
}
#8：其他的一些操作
#删除代码中Log相关的代码
-assumenosideeffects class android.util.Log {
public static boolean isLoggable(java.lang.String, int);
public static int v(...);
public static int d(...);
public static int i(...);
public static int w(...);
public static int e(...);
}
#保留测试相关的代码
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**
