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

#忽略警告
-ignorewarnings
#如果有警告也不终止
-dontwarn
#记录生成的日志数据,gradle build时在本项目根目录输出
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt

# 代码混淆的压缩比例(0-7) , 默认为5 , 一般不需要改
-optimizationpasses 5

# 混淆后类名都小写 (windows最后加上 , 因为windows大小写敏感)
-dontusemixedcaseclassnames

-dontoptimize
# 不做预校验，preverify是proguard的4个步骤之一
# Android不需要preverify，去掉这一步可加快混淆速度
-dontpreverify
#-repackageclasses ''
-allowaccessmodification
-optimizations !code/simplification/arithmetic
-keepattributes *Annotation*

-keep class * implements java.lang.annotation.Annotation
#-keep class * extends java.lang.annotation.Annotation {
#   *;
#}
#-keep interface * extends java.lang.annotation.Annotation

-keepattributes Signature
#-dontwarn
-keepattributes InnerClasses

#抛出异常时保留代码行号，在异常分析中可以方便定位
-keepattributes SourceFile,LineNumberTable

# 有了verbose这句话，混淆后就会生成映射文件
# 包含有类名->混淆后类名的映射关系
# 然后使用printmapping指定映射文件的名称
-verbose
-printmapping proguardMapping.txt

-keepattributesEnclosingMethod



-keep class **.R$* {
    *;
}
-keep public class * extends android.content.ContentProvider


-keep class * extends android.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-keep class android.support.v4.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}
-keep class * extends android.support.v4.app.Fragment {
 public void setUserVisibleHint(boolean);
 public void onHiddenChanged(boolean);
 public void onResume();
 public void onPause();
}

-assumenosideeffects class android.util.Log{
    public static *** d(...);
    public static *** i(...);
    public static *** v(...);
    public static *** w(...);
    public static *** e(...);
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends com.laoyuegou.android.core.services.BaseService

-dontwarn android.support.v4.**
-dontnote android.support.v4.**
-keep class android.support.v4.** { *; }
-dontwarn android.support.v7.**
-dontnote android.support.v7.**
-keep class android.support.v7.** { *; }
-dontwarn android.support.annotation.**
-keep class android.support.annotation.** { *; }
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.** { *; }
-keep class android.support.graphics.drawable.** { *; }
-keep class android.support.design.widget.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留自定义控件(继承自View)不能被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
    *** get* ();
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
# 枚举类不能被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keepclassmembers class ** {
    public void onEvent*(**);
    @org.greenrobot.eventbus.Subscribe <methods>;
}

-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(Java.lang.Throwable);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}


-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#=================  Parcelable  =================
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements android.os.Parcelable {
 public <fields>;
 private <fields>;
}

-keep public class * implements android.os.Parcelable { *; }

-keepclassmembers class * {
    @org.junit.internal.runners.statements.FailOnTimeout *;
}

#=================  Serializable  =================

-keep public class * implements java.io.Serializable { *; }
#-keep public interface * extends java.io.Serializable { *; }

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

 # ================== webview相关 ==================
 -dontwarn android.webkit**

 # WebView(可选)
 -keepclassmembers class * extends android.webkit.WebView {
    public *;
 }
 # WebView的复杂操作
 -keepclassmembers class * extends android.webkit.WebViewClient {
      public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
      public boolean *(android.webkit.WebView,java.lang.String);
 }
 -keepclassmembers class * extends android.webkit.WebChromeClient {
      public void *(android.webkit.WebView,java.lang.String);
 }

 # 与JS交互
 -keepattributes SetJavaScriptEnabled
 -keepattributes JavascriptInterface

 # 保留与JS交互接口 , API17+
 -keepclassmembers class * {
     @android.webkit.JavascriptInterface <methods>;
 }
-dontwarn org.apache.**

#================== androidx ==========================

-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

#================== Lifecycle ==========================
## Android architecture components: Lifecycle
# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver {
    <init>(...);
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
# keep Lifecycle State and Event enums values
-keepclassmembers class androidx.lifecycle.Lifecycle$State { *; }
-keepclassmembers class androidx.lifecycle.Lifecycle$Event { *; }
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @androidx.lifecycle.OnLifecycleEvent *;
}

#==================以上基本不动==========================

#==================以下可选，根据需求==========================

#==================glide==========================
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.**{*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#==================banner==========================
-keep class com.youth.banner.** {
    *;
}
 #================== AgmentWeb ==================
-keep class com.just.agentweb.** {
  *;
}
-dontwarn com.just.agentweb.**
-keepclassmembers class com.just.agentweb.sample.common.AndroidInterface{ *; }

#==================app==========================

-keep class com.lengjiye.code.*.model.** { *; }
-keep class com.lengjiye.code.*.service.** { *; }
-keep class com.lengjiye.code.*.viewmodel.** { *; }
-keep class com.lengjiye.code.*.bean.** { *; }


