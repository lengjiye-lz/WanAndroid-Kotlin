# WanAndroid • kotlin 版本
基于[www.wanandroid.com](https://www.wanandroid.com/)开发App，主要包括首页、广场、体系、项目、我的、搜索等6大模块，第一版主要是练手，界面比较粗糙

项目采用模块化开发，也是为了整理框架

分为base库、网络库、工具库、自动化生成代码工具等

持续更新中...

项目地址：[https://github.com/qiduzao/WanAndroid-Kotlin](https://github.com/qiduzao/WanAndroid-Kotlin)

### 效果图
![Image text](./image/home.jpg)&nbsp;&nbsp; ![Image text](./image/share.jpg)&nbsp;&nbsp; ![Image text](./image/system.jpg)

![Image text](./image/project.jpg)&nbsp;&nbsp; ![Image text](./image/me.jpg)&nbsp;&nbsp; ![Image text](./image/search.jpg)

### 使用到的依赖库

* 项目框架：JetPack

  2. MVVM：项目整体框架
  3. Lifecycle：生命周期管理，主要是网络请求
  4. LiveData：数据通知
  5. Room：数据库

* 网络框架：okhttp3 + retrofit2 + Flow
* 图片框架：glide
* banner：com.youth.banner.Banner
* recycleview刷新加载：com.scwang.smart:refresh-layout-kernel
* webview：com.just.agentweb:agentweb
* 底部切换button：com.ashokvarma.android:bottom-navigation-bar

### 版本迭代记录

* 1.0.0 搭建框架，实现基本功能，组件化开发
* 1.0.1
  1. 添加room数据库，首页四屏都添加数据缓存
  2. 修改数据加载策略
  3. 添加自定义图片圆角
  4. 添加新功能，不需要权限都悬浮球
  5. 添加日志实时显示功能 [LogServiceInstance](https://github.com/lengjiye-lz/WanAndroid-Kotlin/blob/v1.0.1/tools/src/main/java/com/lengjiye/tools/log/LogServiceInstance.kt)
  6. 修改bug
  7. 分离数据总线module。MasterApplication 这个类是 module 之间数据传输的基础，数据总线和生命周期监听也是，都是基础中的基础。
* 2.0.1
  1. 升级依赖库
  2. 去掉rxjava2，改为协程和Flow。因为rxjava一直以来只用到线程切换功能，其他功能基本没有用到，所以舍弃rxjava2。
  3. 修改网络请求框架，改为okhttp3 + retrofit2 + Flow
* 2.0.2
  1. 升级依赖库
  2. 把基础库改成aar，提高编译速度。依赖库地址[base-lib](https://github.com/lengjiye-lz/base-lib)

![Image text](./image/no_bug.jpg)
