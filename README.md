# HotelIntelligenceSystem

#### 介绍
酒店智慧系统

#### 软件架构
![系统架构图](https://gitee.com/tomato-simon/hotel-intelligence-system/raw/dev/temp_image/xtjg.png)


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1. 数据库相关

   - dept表:
     - role列的值用作鉴权，系统设计为admin（管理员）、hotel_admin（酒店管理员）和hotel_member（酒店员工）。若要自定义，需同步修改后端代码。
2. 二维码登录相关
   - 使用轮询方式访问redis（可改为websocket推送），使用UUID的方式生成码作为redis的key，对应的value为0时为未扫描，1为已扫描，2为确认登录
3. xxxx



#### 主要贡献者

**后端（服务端）:**

- tomato-simon (1499602163@qq.com)（main）
- static_move (2562748196@qq.com)

**前端（客户端）：**

- fuxiao_lee (925194376@qq.com)（main）
- tomato-simon (1499602163@qq.com)
- lingyu1412 (9770826+lingyu1412@user.noreply.gitee.com)



#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request




#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目.
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
