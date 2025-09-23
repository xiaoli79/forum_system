## 论坛系统 (Forum System)

这是一个基于Spring Boot和Vue.js（通过CDN引入）构建的前后端分离的现代化Web论坛系统。项目实现了论坛系统的核心功能，包括用户认证、板块浏览、帖子发布与管理、回复评论以及用户间的私信交流。

## 项目简介

本项目是一个功能完善的在线BBS论坛，用户可以注册登录，在不同的板块下发表文章、浏览帖子、进行评论回复。系统还支持用户查看个人主页、编辑个人资料以及进行用户间的私信通信，旨在提供一个完整的社区交流平台。


## 访问网站
我已部署到云服务器上，可以通过这个链接进行访问  http://47.105.69.137:8080/sign-in.html

## 技术栈

* **后端框架:** Spring Boot
* **数据库 ORM:** MyBatis
* **数据库:** MySQL
* **前端框架:** Vue.js, jQuery
* **前端UI库:** Tabler
* **富文本编辑器:** Editor.md, TinyMCE
* **构建工具:** Maven
* **编程语言:** Java

## 项目结构

Markdown

# 论坛系统 (Forum System)

这是一个基于Spring Boot和Vue.js（通过CDN引入）构建的前后端分离的现代化Web论坛系统。项目实现了论坛系统的核心功能，包括用户认证、板块浏览、帖子发布与管理、回复评论以及用户间的私信交流。

## 项目简介

本项目是一个功能完善的在线BBS论坛，用户可以注册登录，在不同的板块下发表文章、浏览帖子、进行评论回复。系统还支持用户查看个人主页、编辑个人资料以及进行用户间的私信通信，旨在提供一个完整的社区交流平台。

## 技术栈

* **后端框架:** Spring Boot
* **数据库 ORM:** MyBatis
* **数据库:** MySQL
* **前端框架:** Vue.js, jQuery
* **前端UI库:** Tabler
* **富文本编辑器:** Editor.md, TinyMCE
* **构建工具:** Maven
* **编程语言:** Java


## 项目结构
```test
forum_system
├── src
│   ├── main
│   │   ├── java/com/forum_system
│   │   │   ├── common          // 公共工具类和结果封装
│   │   │   ├── config          // 配置类 (MyBatis)
│   │   │   ├── controller      // API控制器
│   │   │   ├── dao             // MyBatis Mapper接口
│   │   │   ├── exception       // 全局异常处理
│   │   │   ├── interceptor     // 登录拦截器
│   │   │   ├── model           // 数据实体类
│   │   │   ├── service         // 业务逻辑服务层
│   │   │   └── utils           // 工具类 (MD5, UUID等)
│   │   └── resources
│   │       ├── mapper          // MyBatis XML映射文件
│   │       ├── static          // 存放前端静态资源 (HTML, CSS, JS)
│   │       └── application.yml // Spring Boot 配置文件
│   └── test                    // 单元测试
├── pom.xml                     // Maven 配置文件
└── forum_db.sql                // 数据库初始化脚本
```
## 核心功能

1.  **用户模块 (`UserController`)**
    * 用户注册 (`/user/register`)
    * 用户登录 (`/user/login`)
    * 用户登出 (`/user/logout`)
    * 获取当前登录用户信息 (`/user/get_user_info`)
    * 修改用户个人信息 (`/user/update`)

2.  **板块模块 (`BoardController`)**
    * 获取所有板块列表 (`/board/get_all`)
    * (管理员) 添加/修改板块

3.  **文章模块 (`ArticleController`)**
    * 根据板块ID分页获取文章列表 (`/article/get_by_board`)
    * 根据文章ID获取文章详情 (`/article/details`)
    * 发布新文章 (`/article/add`)
    * 修改文章 (`/article/update`)
    * 删除文章 (`/article/delete`)

4.  **文章回复模块 (`ArticleReplyController`)**
    * 根据文章ID获取所有回复 (`/article_reply/get_by_article_id`)
    * 添加新回复 (`/article_reply/add`)
    * 采纳回复为最佳答案 (`/article_reply/accept`)

5.  **私信模块 (`MessageController`)**
    * 发送私信 (`/message/add`)
    * 获取当前用户的会话列表 (`/message/get_sessions`)
    * 根据会话ID获取具体的聊天记录 (`/message/get_by_session_id`)

## 数据库设计

项目使用MySQL数据库，数据库初始化脚本位于 `src/forum_db.sql`。主要包含以下几张表：

* `user`: 用户信息表，存储用户的基本信息和凭证。
* `board`: 论坛板块表，用于对文章进行分类。
* `article`: 文章表，存储帖子的标题、内容、作者等信息。
* `article_reply`: 文章回复表，存储对文章的评论和回复。
* `message`: 用户私信表，存储用户间的聊天记录。

## 如何运行

1.  **数据库配置**:
    * 在本地MySQL环境中创建一个数据库（例如 `forum_db`）。
    * 执行项目根目录下的 `src/forum_db.sql` 脚本，初始化数据库表和数据。
    * 打开 `src/main/resources/application.yml` 文件，修改 `spring.datasource` 下的数据库连接信息（URL, username, password）以匹配您的本地环境。

2.  **启动后端服务**:
    * 确保本地已安装Java和Maven环境。
    * 在项目根目录 `forum_system` 下，通过命令行执行 `mvn spring-boot:run` 或在IDE中直接运行 `ForumSystemApplication.java`。
    * 服务默认启动在 `8080` 端口。

3.  **访问前端页面**:
    * 后端服务启动后，直接在浏览器中访问 `http://localhost:8080` 即可看到论坛首页。






