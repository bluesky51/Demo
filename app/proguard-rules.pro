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
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5  #代码混淆的压缩比例，值在0-7之间
-ignorewarnings
-dontusemixedcaseclassnames #混淆后类名都为小写
-dontskipnonpubliclibraryclasses #指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclassmembers #指定不去忽略非公共的库的类的成员
-dontpreverify #不做预校验的操作
-verbose #生成原类名和混淆后的类名的映射文件
-printmapping proguardMapping.txt #生成原类名和混淆后的类名的映射文件
-optimizations !code/simplification/cast,!field/*,!class/merging/* #指定混淆是采用的算法
-keepattributes *Annotation*,InnerClasses #不混淆Annotation
-keepattributes Signature #不混淆泛型
-keepattributes SourceFile,LineNumberTable #抛出异常时保留代码行号
-keepattributes *JavascriptInterface*
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep class com.gyf.barlibrary.* {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
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
# 移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用
# 记得proguard-android.txt中一定不要加-dontoptimize才起作用
# 另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
#-assumenosideeffects class android.util.Log {
#    public static int v(...);
#    public static int i(...);
#    public static int w(...);
#    public static int d(...);
#    public static int e(...);
#}

#############################################
#
# 项目中特殊处理部分
#
#############################################

#-----------处理反射类---------------



#-----------处理js交互---------------
#第三部分与js互调的类，工程中没有直接跳过。一般你可以这样写
#-keep class cn.com.bestbuy.ui.web.** { *; }

#如果是内部类的话，你可以这样
#      <methods>;
-keepclasseswithmembers class cn.com.bestbuy.ui.web.WebActivity$DemoJavaScriptInterface {
     public *;
}
#-----------处理实体类---------------
# 在开发的时候我们可以将所有的实体类放在一个包内，这样我们写一次混淆就行了。
-keep class com.sky.wang.model.**{ *; }


#-----------处理第三方依赖库---------
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

#****************************rxJava和rxandroid代码混淆开始******************************
-dontwarn rx.*
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQuene*Field*{
long producerIndex;
long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
rx.internal.util.atomic.LinkedQueueNode producerNode;
rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#****************************rxJava和rxandroid代码混淆结束******************************

#****************************Glide代码混淆开始******************************
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
#****************************Glide代码混淆结束******************************

#****************************okhttp3代码混淆开始******************************
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
#0khttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
#okio
-dontwarn okio.**
#****************************okhttp3代码混淆结束******************************

#****************************okhttpInterceptor代码混淆开始******************************
-dontwarn okhttp3.logging.**
#****************************okhttpInterceptor代码混淆结束******************************

#****************************retrofit2代码混淆开始******************************
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
-keepattributes Exceptions
# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
#****************************retrofit2代码混淆结束******************************

#****************************adapter-rxjava2代码混淆开始******************************

#****************************adapter-rxjava2代码混淆结束******************************

#****************************converter-gson代码混淆开始******************************

#****************************converter-gson代码混淆结束******************************

#****************************multiple-status-view代码混淆开始******************************
-keep class com.classic.common.** { *; }
-dontwarn com.classic.common.*
#****************************multiple-status-view代码混淆结束******************************

#****************************dexter代码混淆开始******************************
-renamesourcefileattribute SourceFile

# Preserve all Dexter classes and method names

-keepattributes InnerClasses, Signature, *Annotation*

-keep class com.karumi.dexter.** { *; }
-keep interface com.karumi.dexter.** { *; }
-keepclasseswithmembernames class com.karumi.dexter.** { *; }
-keepclasseswithmembernames interface com.karumi.dexter.** { *; }
#****************************dexter代码混淆结束******************************

#****************************rxlifecycle代码混淆开始******************************
-keep class com.trello.rxlifecycle.** { *; }
-keep interface com.trello.rxlifecycle.** { *; }
#****************************rxlifecycle代码混淆结束******************************

#****************************rxlifecycle-components代码混淆开始******************************

#****************************rxlifecycle-components代码混淆结束******************************

#****************************multidex代码混淆开始******************************

#****************************multidex代码混淆结束******************************

#****************************BaseRecyclerViewAdapterHelper代码混淆开始******************************
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
#****************************BaseRecyclerViewAdapterHelper代码混淆结束******************************
#****************************com.wang.avi:library代码混淆开始******************************
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }
#****************************com.wang.avi:library代码混淆结束******************************

#****************************eventbus代码混淆开始******************************
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#****************************eventbus代码混淆结束******************************

#****************************aspectjrt代码混淆开始******************************

#****************************aspectjrt代码混淆结束******************************

#****************************leakcanary代码混淆开始******************************
-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }

# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification
#****************************leakcanary代码混淆结束******************************

#****************************Gson代码混淆开始******************************
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}
-keep public class com.project.mocha_patient.login.SignResponseData { private *; }
#****************************gson代码混淆结束******************************

#****************************DataBinding代码混淆开始******************************
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
-keep class com.sky.wang.databinding.** {
    <fields>;
    <methods>;
}
-keep class com.sensorsdata.analytics.android.** { *; }
#****************************DataBinding代码混淆结束******************************
