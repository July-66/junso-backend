package com.zjh.junsobackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjh.junsobackend.pojo.entity.Post;
import com.zjh.junsobackend.pojo.req.post.PostQueryRequest;
import com.zjh.junsobackend.pojo.vo.PostVo;

import java.util.List;

/**
* @author 86133
* @description 针对表【post(帖子)】的数据库操作Service
* @createDate 2023-03-10 22:42:17
*/
public interface PostService extends IService<Post> {

    /**
     * 根据传入的数据是否为空动态生成queryWrapper对象
     * @param postQueryRequest
     * @return
     */
    QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest);

    /**
     * 获取postList对应的PostVoList
     * @param postList
     * @return
     */
    List<PostVo> getPostVO(List<Post> postList);

    /**
     * 从Es里获取数据并封装为Vo对象
     * @param postQueryRequest
     * @return Page<PostVo>
     */
    Page<Post> getFromEs(PostQueryRequest postQueryRequest);
}
