# IYingLibrary
框架

为了不让应用频繁发生崩溃，做了一些特殊处理

最主要的核心是添加安全方法和节省代码量

# 使用
需要在Application的onCreate方法中通过LibConstant.setContext()方法实现初始化操作

# 分类
 
BaseUtiltiy          -->>  一些常用方法，如setText，getText等
AndroidUtility       -->>  获取手机设备信息
ScreenUtility        -->>  获取屏幕相关信息，如屏幕宽高，状态栏高度，px和dp的转化
ListenerUtility      -->>  添加监听的安全方法，如点击事件，长按事件，AbsListView的item点击事件，触摸事件等

