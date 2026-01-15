-keepclassmembers class com.alain.foundyou.data.network.model.** { *; }
-keep public class com.alain.foundyou.data.network.model.**

-keepattributes Signature
-keepattributes *Annotation*
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

-keep class * implements dagger.hilt.internal.GeneratedComponent
-keep class * implements dagger.hilt.internal.GeneratedEntryPoint
-keep class * implements dagger.hilt.internal.GeneratedComponentManager
-keep class * implements dagger.hilt.internal.GeneratedComponentManagerHolder
-keep class dagger.hilt.internal.processedrootsentinel.ProcessedRootSentinel