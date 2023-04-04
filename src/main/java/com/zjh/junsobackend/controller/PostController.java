package com.zjh.junsobackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjh.junsobackend.common.BaseResponse;
import com.zjh.junsobackend.pojo.vo.PostVo;
import com.zjh.junsobackend.util.ResultUtils;
import com.zjh.junsobackend.pojo.entity.Post;
import com.zjh.junsobackend.pojo.req.post.PostQueryRequest;
import com.zjh.junsobackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/list/page/vo")
    public BaseResponse<Page<PostVo>> listPostVoByPage (@RequestBody PostQueryRequest postQueryRequest){
        int current = postQueryRequest.getCurrent();
        long pageSize = postQueryRequest.getPageSize();
        Page<Post> page = postService.page(new Page<>(current, pageSize), postService.getQueryWrapper(postQueryRequest));
        Page<PostVo> postVoPage = new Page<>(current, pageSize, page.getTotal());
        postVoPage.setRecords(postService.getPostVO(page.getRecords()));
        return ResultUtils.success(postVoPage);

    }
}




