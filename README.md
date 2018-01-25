# commonUtils
Android 收集常用的工具类的库
# 添加依赖
```java
compile 'com.vinpin:commonutils:1.0.0'
```
# 使用
尽可能早的初始化，建议放在Application的onCreate()中
```java
@Override
public void onCreate() {
    super.onCreate();
    ...
    // 初始化
    Utils.init(this);
    ...
}
```
