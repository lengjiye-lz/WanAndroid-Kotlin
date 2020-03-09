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

#==================okio-1.6.0.jar==========================
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**
-keep class okio.** { *; }

#==================okhttp-3.2.0.jar==========================
-dontwarn okhttp3.**
-dontnote okhttp3.**
-keep class okhttp3.** { *; }
-keep class okhttp3.internal.framed.** { *; }
-keep class okhttp3.internal.http.** { *; }
-keep class okhttp3.internal.io.** { *; }
-keep class okhttp3.internal.tls.** { *; }
-keep class okhttp3.internal.** { *; }


#==================gson==========================
-keep class sun.misc.Unsafe { *; }
-dontwarn com.google.**
-dontnote com.google.**
-keep class com.google.gson.** {*;}

-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

#==================retrofit==========================
-dontnote retrofit2.**
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
-keepattributes Exceptions
-dontwarn okio.**

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
#-keep androidx.lifecycle.*

-keep class com.lengjiye.network.** {*;}
-keep class com.lengjiye.utils.** {*;}

-dontwarn autodispose2.KotlinExtensions
