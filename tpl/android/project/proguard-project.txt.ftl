# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-repackageclasses ''
-allowaccessmodification
-optimizations !code/simplification/arithmetic

#-keep class javax.** { *; }
#-keep class org.** { *; }

# Keep line numbers so they appear in the stack trace of the develeper console
-keepattributes SourceFile,LineNumberTable

# Google Guava
# see http://code.google.com/p/guava-libraries/wiki/UsingProGuardWithGuava
#-keep public class com.google.common.**
-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue

# Joda
-keep public class org.joda.time.** { public protected *; }
-dontwarn org.joda.convert.FromString
-dontwarn org.joda.convert.ToString

# Harmony
-dontwarn com.tactfactory.harmony.**

# Facebook
#-keep class com.facebook.** { *; }
#-dontwarn com.facebook.**

# Twitter
#-keep class twitter4j.** { *; }

# Flurry
#-dontwarn com.flurry.**

# JDom2
-keep class org.jdom2.** { *; }
-dontwarn org.jdom2.**

-target 1.6

# ActionBarSherlock
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.content.Loader
-keep class android.support.v4.widget.CursorAdapter
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
