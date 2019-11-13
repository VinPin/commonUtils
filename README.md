# commonUtils
Android 收集常用的工具类的库

## 添加依赖

Gradle:
```groovy
// 支持AndroidX
compile 'com.vinpin:commonutils:2.0.0'
// 非支持AndroidX
compile 'com.vinpin:commonutils:1.0.5'
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

* ### [Utils.java][utils.java]
```
init   : 初始化工具类
getApp : 获取ApplicationContext
```

* ### [AppManager.java][appManager.java]
```
getInstance                 : 单例模式，单一实例
addActivity                 : 添加Activity到堆栈
removeActivity              : 移除Activity出堆栈
currentActivity             : 获取当前Activity（堆栈中最后一个压入的）
finishActivity              : 结束Activity
hasActivity                 : 是否包含指定的activity
finishActivityOfClass       : 结束指定类名的所有Activity
finishAllActivity           : 结束所有Activity
finishActivitysExceptAssign : 结束所有Activity,除了指定的Activity
AppExit                     : 退出应用程序
getActivityCount            : 获取Activity数量
```

* ### [AppUtils.java][appUtils.java]
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

* ### [EmptyUtils.java][emptyUtils.java]
```
isEmpty    : 判断对象是否为空
isNotEmpty : 判断对象是否非空
```

* ### [FileUtils.java][fileUtils.java]
```
getFileByPath             : 根据文件路径获取文件
getPathByFile             : 根据文件获取文件路径
isFileExists              : 判断文件是否存在
rename                    : 重命名文件
isDir                     : 判断是否是目录
isFile                    : 判断是否是文件
createOrExistsDir         : 判断目录是否存在，不存在则判断是否创建成功
createOrExistsFile        : 判断文件是否存在，不存在则判断是否创建成功
createFileByDeleteOldFile : 判断文件是否存在，存在则在创建之前删除
deleteDir                 : 删除目录
deleteFile                : 删除文件
getFileLength             : 获取文件大小
getDirLength              : 获取文件夹大小
byte2FitMemorySize        : 字节数转合适内存大小
```

* ### [KeyboardUtils.java][keyboardUtils.java]
```
showSoftInput   : 动态显示软键盘
hideSoftInput   : 动态隐藏软键盘
toggleSoftInput : 切换键盘显示与否状态
```

* ### [NetworkUtils.java][networkUtils.java]
```
openWirelessSettings   : 打开网络设置界面
isConnected            : 判断网络是否连接
isWifiConnected        : 判断wifi是否连接状态
getWifiEnabled         : 判断wifi是否打开
setWifiEnabled         : 打开或关闭wifi
is4G                   : 判断网络是否是4G
getNetworkOperatorName : 获取网络运营商名称
getNetworkType         : 获取当前网络类型
```

* ### [RegexUtils.java][regexUtils.java]
```
isMatch : 判断是否匹配正则
```

* ### [ResourcesUtils.java][resourcesUtils.java]
```
getString      : 获取资源字符串
getStringArray : 获取资源字符串数组
getColor       : 获取颜色值
getDrawable    : 获取drawable
```

* ### [ScreenUtils.java][screenUtils.java]
```
getScreenWidth  : 获取屏幕的宽度（单位：px）
getScreenHeight : 获取屏幕的高度（单位：px）
setFullScreen   : 设置屏幕为全屏
isLandscape     : 判断是否横屏
isPortrait      : 判断是否竖屏
isScreenLock    : 判断是否锁屏
isTablet        : 判断是否是平板
screenShot      : 截屏
getScreenParams : 获取屏幕相关的信息
```

* ### [SDCardUtils.java][sdCardUtils.java]
```
isSDCardEnable     : 判断SD卡是否可用
getSDCardPath      : 获取SD卡路径
getFreeSpace       : 获取SD卡剩余空间
getRootCachePath   : 获取app缓存存储根路径
getCachePath       : 获取app缓存存储根路径下cache文件夹下
```

* ### [SizeUtils.java][sizeUtils.java]
```
dp2px             : dp转px
px2dp             : px转dp
sp2px             : sp转px
px2sp             : px转sp
getScreenPx       : 获取屏幕尺寸
measureView       : 测量视图尺寸
getMeasuredWidth  : 获取测量视图宽度
getMeasuredHeight : 获取测量视图高度
```

* ### [SpanUtils.java][spanUtils.java]
```
append             : 追加样式字符串
setFlag            : 设置标识
setForegroundColor : 设置前景色
setBackgroundColor : 设置背景色
setProportion      : 设置字体比例
setXProportion     : 设置字体横向比例
setStrikethrough   : 设置删除线
setUnderline       : 设置下划线
create             : 创建样式字符串
```

* ### [StatusBarUtils.java][statusBarUtils.java]
```
setStatusBarColor     : 设置系统状态栏的颜色
setStatusBarLightMode : 设置亮色状态栏模式
```

* ### [StringUtils.java][stringUtils.java]
```
isEmpty        : 判断字符串是否为null或长度为0
isTrimEmpty    : 判断字符串是否为null或全为空格
isSpace        : 判断字符串是否为null或全为空白字符
nullIfEmpty    : null转为长度为0字符串转为null的字符串
emptyIfNull    : null转为长度为0的字符串
length         : 返回字符串长度
subStr         : 按字节数截取某个长度的字符串
getStringBytes : 获取字符串的字节数
```

* ### [TimeUtils.java][timeUtils.java]
```
dateToString    : 日期转换字符串
stringToDate    : 字符串转换日期
stringToLong    : 字符串转换long类型
getStandardTime : 将日期格式化成 yyyy-MM-dd HH:mm
```

[utils.java]:https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/Utils.java
[appManager.java]:https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/AppManager.java
[appUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/AppUtils.java
[emptyUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/EmptyUtils.java
[fileUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/FileUtils.java
[keyboardUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/KeyboardUtils.java
[networkUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/NetworkUtils.java
[regexUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/RegexUtils.java
[resourcesUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/ResourcesUtils.java
[screenUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/ScreenUtils.java
[sdCardUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/SDCardUtils.java
[sizeUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/SizeUtils.java
[spanUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/SpanUtils.java
[statusBarUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/StatusBarUtils.java
[stringUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/StringUtils.java
[timeUtils.java]: https://github.com/VinPin/commonUtils/blob/master/common-utils/src/main/java/com/vinpin/commonutils/TimeUtils.java
