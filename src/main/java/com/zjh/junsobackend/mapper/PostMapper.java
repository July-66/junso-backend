package com.zjh.junsobackend.mapper;

import com.zjh.junsobackend.pojo.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;

/**
* @author 86133
* @description 针对表【post(帖子)】的数据库操作Mapper
* @createDate 2023-03-10 22:42:17
* @Entity com.zjh.junsobackend.pojo.entity.Post
*/
public interface PostMapper extends BaseMapper<Post> {
    List<Post> listPostByUpdateTime(Date minUpdateTime);
}




