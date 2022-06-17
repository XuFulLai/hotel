# Hotel-Intelligence-System

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

------



### 主要贡献者

#### **后端（服务端）:**

- **tomato-simon (1499602163@qq.com)（main）**
- static_move (2562748196@qq.com)

#### **前端（客户端）：**

- **fuxiao_lee (925194376@qq.com)（main）**
- tomato-simon (1499602163@qq.com)（Secondary main）

#### **APP端（客户端）:**

- **houttuynia（[327849132@qq.com](mailto:327849132@qq.com)）（main）**
- tomato-simon ([1499602163@qq.com](mailto:1499602163@qq.com))（part of Scan-code-login）

------




### 软件架构
![系统架构图](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/xtjg.png)

------



### 酒店2.0更新详情

1. 增加了APP端
2. 门户端增加国际化，实现汉英转换
3. 门户端增加页面自适应（兼容手机、平板等设备）
4. 门户端酒店详情页完善
5. 增加了订单评价功能
6. 增加了收藏功能
7. 增加了酒店列表定位功能，现在可以搜索某个范围内的酒店了
8. 增加了钱包模块
9. 增加了优惠券模块
10. 增加了日志管理模块
11. 增加了防疫相关模块
12. 更多更新自行摸索...



### 安装必看

#### 数据库

创建完数据库后，把根目录下的 **hotel-plus.sql** 导入即可

注：开发环境数据库版本为 Ver 8.0.24

#### hotel-rear-end：

1. 将 **.\hotel-plus\hotel-rear-end\src\main\resources** 下的application-base.yml改名为application.yml，并将里面 “####” 替换成描述的对应信息
   1. 其中，邮箱相关请进入邮箱开通POP3 / IMAP / SMTP服务，并获取授权码，有更多邮箱可以按格式增加
   2. 七牛云账号需要自行申请，现在存储方式为先调用 **upload/img** 接口，然后获取url再存进数据库，若需要存储本地，修改前端对应逻辑即可

#### hotel-front-end & hotel-portal：

1.  hotel(front-end)项目安装依赖可能会报错，目前有一种解决方法：删除掉该目录下的package.json文件第15行代码,"vue-qr": "^3.2.4",然后终端键入命令：npm install,成功安装之后，在终端输入命令：npm install vue-qr，安装完成，完成该项目依赖的安装。
2.  由于Websocket是使用get请求去握手的，所以单独需要在“hotel(front-end)/src/components/RoomManage.vue”文件下的initWebsocket()方法下修改服务端ip地址。其他ip地址到对应的request.js中或者application.yaml中修改即可

------



### 使用说明

1. 系统内可用的账号密码（账号/密码）

   1. **管理员：**

      admin/123456

   2. **酒店管理员：**

      1. OneOneTwo国际度假酒店：

         hotel_admin/123456

      2. SixOneFour国际度假酒店：

         sof_hotel_admin/123456

   3. **防疫人员：**

      fangyi1/123456

   4. **普通成员：**

      1. 普通成员1：

         jing/123

      2. 普通成员2：

         lee/123

2. 数据库相关

   - dept表:
     - role列的值用作鉴权，系统设计为admin（管理员）、hotel_admin（酒店管理员）、hotel_member（酒店员工）和anti-epidemic（防疫人员）。若要自定义，需同步修改后端代码。

3. 二维码登录相关

   - 使用轮询方式访问redis（可改为websocket推送），使用UUID的方式生成码作为redis的key，对应的value为0时为未扫描，1为已扫描，2为确认登录

------



### 项目预览

![首页](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/sy1.png)

![首页](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/menu.png)

![首页](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/room.png)

![首页](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/order.png)

![首页](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/dept.png)

------



### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
