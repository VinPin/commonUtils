# commonUtils
Android 收集常用的工具类的库
## 添加依赖

Gradle:
```groovy
compile 'com.blankj:utilcode:1.12.5'
```

## 开始使用

```
// 尽可能早的初始化在Application的onCreate()中
Utils.init(this);
```

## 混淆配置

```
-keep class com.vinpin.commonutils.** { *; }
-keepclassmembers class com.vinpin.commonutils.** { *; }
-dontwarn com.vinpin.commonutils.**
```

## 相关APIs

* ### AppManager 相关 -> [AppManager.java][appManager.java]
```
getInstance                 : 单例模式，单一实例
addActivity                 : 添加Activity到堆栈
removeActivity              : 移除Activity出堆栈
currentActivity             : 获取当前Activity（堆栈中最后一个压入的）
finishActivity              : 结束当前Activity（堆栈中最后一个压入的）
finishActivity              : 结束指定的Activity
finishActivity              : 结束指定类名的Activity
hasActivity                 : 是否包含指定的activity
finishActivityOfClass       : 结束指定类名的所有Activity
finishAllActivity           : 结束所有Activity
finishActivitysExceptAssign : 结束所有Activity,除了指定的Activity
AppExit                     : 退出应用程序
getActivityCount            : 获取Activity数量
```

* ### AppUtils 相关 -> [AppUtils.java][appUtils.java]
```
getPackageName       : 获取App包名
getAppName           : 获取App名称
getAppIcon           : 获取App图标
getVersionName       : 获取App版本号
getVersionCode       : 获取App版本码
isAppDebug           : 判断App是否是Debug版本
isAppForeground      : 判断App是否处于前台
isAppBackground      : 判断App是否处于后台
isActivityForeground : 判断某个界面是否在前台
installApp           : 安装App（支持7.0）
toAppSetingInfo      : 跳转应用信息界面
openBrowser          : 打开浏览器
```

* ### EmptyUtils 相关 -> [EmptyUtils.java][emptyUtils.java]
```
isEmpty    : 判断对象是否为空
isNotEmpty : 判断对象是否非空
```


[appManager.java]:https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/AppManager.java
[appUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/AppUtils.java
[emptyUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/EmptyUtils.java
