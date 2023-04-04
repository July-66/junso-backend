package com.zjh.junsobackend.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zjh.junsobackend.pojo.entity.Post;
import com.zjh.junsobackend.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 初始化帖子列表
 *
 * 实现了CommandLineRunner接口并注入到容器中后每次启动程序都会执行一次run方法
 */
//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Autowired
    PostService postService;

    @Override
    public void run(String... args) {
        for (int i = 1; i < 6; i++) {
            String json = "{\"current\":"+ i +",\"pageSize\":8,\"sortField\":\"_score\",\"sortOrder\":\"descend\",\"searchText\":\"\",\"category\":\"文章\",\"reviewStatus\":1}";
            String url = "https://www.code-nav.cn/api/post/search/page/vo";
            String result = HttpRequest
                    .post(url)
                    .body(json) //body中传入json数据
                    .execute()
                    .body();//获取返回值中body里的内容
            Map<String, Object> map = JSONUtil.toBean(result, Map.class);
            JSONObject data = (JSONObject)map.get("data");
            JSONArray records = (JSONArray)data.get("records");

            List<Post> postList = new ArrayList<>();
            for (Object record : records) {
                JSONObject tempRecord = (JSONObject) record;
                Post post = new Post();
                post.setTitle(tempRecord.getStr("title"));
                post.setContent(tempRecord.getStr("content"));
                post.setTags(tempRecord.getStr("tags"));
                post.setUserid(1L);
                postList.add(post);
            }
            postService.saveBatch(postList);
        }
    }
}
