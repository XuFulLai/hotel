# HotelIntelligenceSystem

### 介绍
> 酒店智慧系统，于2021.12完成。
>
> a hotel-intelligence-system , finish on 2021.12

这也算是一个练手用的小项目，以Springboot集SpringSecurity、redis等多个中间件为载体，做出一个有app端、web端的酒店管理系统 + 类携程的酒店下单门户

其中

1. **SpringSecurity**使用**token**写入过期时间进行有效期管理（SpringSecurity结合Redis进行token有效期管理请移步我的另一个项目——[小区智能管理系统](https://gitee.com/tomato-simon/community-management-system)）
2. 可以通过角色权限或者指定人员授予菜单权限来实现**动态菜单**
3. 引用第三方对象存储库（**七牛云**）来进行音图存储产生外链
4. 实现**二维码登录**功能（包括app端的扫码和登录页的二维码生成）
5. 利用Easy Excel进行**excel表格的读写操作**，实现数据导入导出处理（使用Apache Poi的案例可以移步我的另一个项目——[小区智能管理系统](https://gitee.com/tomato-simon/community-management-system)）
6. 拥有完整的用户网上下单到入住、用户前台直接入住的流程
7. 拥有可改进的房间分配流程
8. 拥有可改进的订单优惠计费功能
9. 后台管理端拥有基于**e-chart**的订单大屏可视化，其中有以地图形式的订单来源地统计、有条形图+折线图混合的订单量和营业额统计、有各房型订单统计的玫瑰图等
10. 把日志分门别类嵌入与各个模块中，形成一个可供管理员筛查的日志管理系统
11. ......


### 软件架构
![系统架构图](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/xtjg.png)




### 安装教程

1.  hotel(front-end)项目安装依赖可能会报错，目前有一种解决方法：删除掉该目录下的package.json文件第15行代码,"vue-qr": "^3.2.4",然后终端键入命令：npm install,成功安装之后，在终端输入命令：npm install vue-qr，安装完成，完成该项目依赖的安装。
2.  由于Websocket是使用get请求去握手的，所以单独需要在“hotel(front-end)/src/components/RoomManage.vue”文件下的initWebsocket()方法下修改服务端ip地址。其他ip地址到对应的request.js中或者application.yaml中修改即可
3.  xxxx



### 使用说明

1. 数据库相关

   - dept表:
     - role列的值用作鉴权，系统设计为admin（管理员）、hotel_admin（酒店管理员）和hotel_member（酒店员工）。若要自定义，需同步修改后端代码。
2. 二维码登录相关
   - 使用轮询方式访问redis（可改为websocket推送），使用UUID的方式生成码作为redis的key，对应的value为0时为未扫描，1为已扫描，2为确认登录
3. xxxx



### 主要贡献者

#### **后端（服务端）:**

- **tomato-simon (1499602163@qq.com)（main）**
- static_move (2562748196@qq.com)

#### **前端（客户端）：**

- **fuxiao_lee (925194376@qq.com)（main）**
- tomato-simon (1499602163@qq.com)（Secondary main）
- lingyu1412 (9770826+lingyu1412@user.noreply.gitee.com)



### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request



### 项目预览

![首页](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/sy1.png)

![首页](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/menu.png)

![首页](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/room.png)

![首页](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/order.png)

![首页](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/dept.png)




### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目.
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
