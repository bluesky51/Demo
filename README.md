#### 1.项目框架模式：MVP  

> 1.1 MVP简介  

```
1.1-1 View:View通常来说是由Activity实现的，它会包含一个Presenter的引用，
           View要做的就只是在每次有接口调用的时候（比如按钮点击后）调用Presenter的方法。   
1.1-2 Model:业务逻辑和实体模型  
1.1-3 Presenter：主要作为沟通View和Model的桥梁，它从Model层检索数据后，返回给View层，
                 但是不像MVC结构，因为它也可以决定与View层的交互操作。
```

>  1.2 该模式的优势：降低耦合度,实现了Model和View真正的完全分离,可以修改View而不影响Modle,模块职责划分明显，层次清晰，代码更加灵活等  

>    1.3 在该项目中主要封装了很多基类(BaseMvpView,BaseMvpPresenter,BaseMvpModel,BaseMvpFactory等类)，

 **业务功能的实现需要继承或者实现封装的这些类(Activity继承AbstractMvpAppCompatActivity,Fragment继承AbstractFragment，View实现BaseView接口)，框架还会自动管理生命周期和通过代理进行Presenter的注入**   

#### 2.一些配置信息及第三方依赖统一管理:使用google支持的自定义config.gradle完成   

#### 3.项目日志框架库：logger,提供的功能：

​        3.1 线程的信息  
        3.2 类的信息  
        3.3 方法的信息  
        3.4 将 JSON 文本人性化输出  
        3.5 将换行符人性化输出  
        3.6 简洁的输出  
        3.7 从日志跳转到源码  
   优点：显示更加清晰人性化  

#### 4.项目网络请求框架

> ​Retrofit(Okhttp+(Okhttp logging interceptor))+RxJava+RxAndroid+RxLifecycle      

```
 4.1 Retrofit 是一个 RESTful 的 HTTP 网络请求框架的封装。(网络请求的工作本质上是OkHttp完成，而Retrofit仅负责网络请求接口的封装)  
     4.2 RxJava+RxAndroid:函数式响应编程思想.提供很多常见操作符链式完成以数据流为核心，处理数据的输入，处理以及输出的模式。  
     4.3 使用RxLifeCycle是因为在使用Rxjava的过程中,当发布一个订阅后,页面被finsh,此时订阅的逻辑还没完成,容易引发内存泄漏的问题.  
         所以RxLifeCircle可以通过compose()手动/自动关闭没完成的逻辑对象  
```

#### 5.解析请求数据结果：Gson  

​     Gson 是google解析Json的一个开源框架,同类的框架fastJson,JackJson等等  

#### 6.图片加载：Glide  

​     Gilde 是google开源的图片加载库。  
  备注：圆角图片处理：com.makeramen:roundedimageview  
             圆形图片处理：de.hdodenhof:circleimageview  

#### 7.UI方面：  

​    7.1 BaseRecyclerViewAdapterHelper 一个强大并且灵活的RecyclerViewAdapter。  
         它可以大量减少你Adapter写的代码（和正常的Adapter相比至少三分之二的）  
         它可以很轻松的添加RecyclerView加载动画,添加item点击事件,新增添加头部、添加尾部  
         新增下拉刷新、上拉加载更多,新增分组,自定义item类型  
    7.2 multiple-status-view:一个支持多种状态的自定义View(加载中视图,错误视图,空数据视图,网络异常视图,内容视图)  
    7.3 com.wang.avi.AVLoadingIndicatorView漂亮的动画加载库  

 ........后续在添加  

#### 8.动态申请权限：Dexter

​      Dexter:在运行时简化了请求权限的过程,用户可以选择开启或者拒绝权限的使用

#### 9.应用程序的各组件、组件与后台线程间进行通信:eventbus

   特点:8.1 简化通信过程，代码简单
           8.2 有效分离事件发送方和接收方(解耦)
           8.3 能避免复杂和容易出错的依赖性和生命周期问题。

#### 10.aspectjrt引入切面编程AOP

​     AOP :通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术
          利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，
          提高程序的可重用性，同时提高了开发的效率。  

  目的： 将业务功能的关注点和通用化功能的分离开来(例如：每次网络请求都需要判断是否有网络，每次请求网络都要提示用户等待等。。。)  

#### 11.leakcanary进行内存泄漏检测分析处理  

#### 12.屏幕适配：使用DimenTool工具类生成不同屏幕分辨率的dimens.xml文件  

#### 13.增量更新和热更新  

   增量更新：1。用户手机上提取当前安装应用的apk  
                      2。利用old.apk和new.apk生成增量文件  
                      3。增加文件与1.中的old.apk合并，然后安装  

   增量文件的生成与合并：利用工具做二进制的一个diff和patch  
