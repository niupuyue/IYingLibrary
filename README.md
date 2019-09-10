# IYingLibrary
框架

为了不让应用频繁发生崩溃，做了一些特殊处理

最主要的核心是添加安全方法和节省代码量

# 使用
需要在Application的onCreate方法中通过LibConstant.setContext()方法实现初始化操作

# 分类

### utils工具类
 
BaseUtiltiy          -->>  一些常用方法，如setText，getText等

AndroidUtility       -->>  获取手机设备信息

ScreenUtility        -->>  获取屏幕相关信息，如屏幕宽高，状态栏高度，px和dp的转化

ListenerUtility      -->>  添加监听的安全方法，如点击事件，长按事件，AbsListView的item点击事件，触摸事件等

AudioCaptureUtility  -->>  语音录制

AudioPlayUtility     -->>  语音播放

CustomToastUtility   -->>  自定义Toast弹窗

FileUtility          -->>  文件操作相关方法

LibraryConstants     -->>  获取全局ApplicationContext对象

LoggerUtility        -->>  日志输出

NetStatusUtility     -->>  网络状态

NotificationUtility  -->>  Notification通知管理类

PermissionUtility    -->>  权限管理工具类

ScreenUtility        -->>  手机屏幕相关操作工具

SharedpreferencesUtility     -->>  手机Shared存储

TimeUtility          -->>  时间操作工具类

### widgets自定义View类

BaseDialog           -->>  Dialog弹窗基类

datepicker           -->>  时间选择弹窗

CircleImageView      -->>  圆形图片

NoSlidingViewpager   -->>  取消左右滑动的viewpager

PasteListenerEditTextView    -->>  带有粘贴效果的输入框

RoundLinearLayout    -->>  圆角LinearLayout布局

SimpleDialg          -->>  一些只有确定和取消两个按钮的弹窗dialog(里面的方法可以直接使用)

SingleLineZoomTextSizeTextView     -->>  单行显示文本，如果文本长度不够，动态修改字体大小

SpanClickAble        -->>  突出文字的点击事件

### https类

YingHttp             -->>  封装OkHttp网络请求(未完成)
