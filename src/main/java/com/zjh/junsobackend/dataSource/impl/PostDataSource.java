package com.zjh.junsobackend.dataSource.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjh.junsobackend.dataSource.DataSource;
import com.zjh.junsobackend.pojo.entity.Post;
import com.zjh.junsobackend.pojo.req.post.PostQueryRequest;
import com.zjh.junsobackend.pojo.vo.PostVo;
import com.zjh.junsobackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostDataSource implements DataSource<PostVo> {

    @Autowired
    PostService postService;

    @Override
    public Page<PostVo> doSearch(String searchText, int current, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(current);
        postQueryRequest.setPageSize(pageSize);
        Page<Post> postPage = postService.getFromEs(postQueryRequest);
        Page<PostVo> postVoPage = new Page<>(current, pageSize, postPage.getTotal());
        postVoPage.setRecords(postService.getPostVO(postPage.getRecords()));
        return postVoPage;
    }
}
