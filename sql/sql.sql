-- 切换库
use junso_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
    ) comment '用户' collate = utf8mb4_unicode_ci;

INSERT INTO user (userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole)
VALUES
('张三', '123456', 'unionid1', 'mpopenid1', '小黑子', 'https://example.com/avatar1.jpg', '我喜欢打篮球和rapper', 'user'),
('李四', 'abcdefg', NULL, NULL, '李四', 'https://example.com/avatar2.jpg', '我是一名音乐家，爱好弹钢琴和吉他', 'user'),
('王五', 'password123', 'unionid3', NULL, '王五', 'https://example.com/avatar3.jpg', '我喜欢阅读和旅游', 'admin');
INSERT INTO user(userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole) VALUES ('张伟', '123456', 'union_id_1', 'mp_open_id_1', '张伟', 'http://example.com/avatar_1.jpg', '张伟是一个Web开发工程师', 'user');
INSERT INTO user(userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole) VALUES ('王芳', '123456', 'union_id_2', 'mp_open_id_2', '王芳', 'http://example.com/avatar_2.jpg', '王芳是一名产品经理', 'user');
INSERT INTO user(userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole) VALUES ('李勇', '123456', 'union_id_3', 'mp_open_id_3', '李勇', 'http://example.com/avatar_3.jpg', '李勇是一名销售', 'user');
INSERT INTO user(userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole) VALUES ('刘娜', '123456', 'union_id_4', 'mp_open_id_4', '刘娜', 'http://example.com/avatar_4.jpg', '刘娜是一名人力资源经理', 'user');
INSERT INTO user(userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole) VALUES ('王浩', '123456', 'union_id_5', 'mp_open_id_5', '王浩', 'http://example.com/avatar_5.jpg', '王浩是一名设计师', 'user');
INSERT INTO user(userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole) VALUES ('张秀兰', '123456', 'union_id_6', 'mp_open_id_6', '张秀兰', 'http://example.com/avatar_6.jpg', '张秀兰是一名财务经理', 'user');
INSERT INTO user(userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole) VALUES ('李宁', '123456', 'union_id_7', 'mp_open_id_7', '李宁', 'http://example.com/avatar_7.jpg', '李宁是一名物流经理', 'user');
INSERT INTO user(userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole) VALUES ('陈明', '123456', 'union_id_8', 'mp_open_id_8', '陈明', 'http://example.com/avatar_8.jpg', '陈明是一名客服', 'user');

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
    ) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
    ) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
    ) comment '帖子收藏';