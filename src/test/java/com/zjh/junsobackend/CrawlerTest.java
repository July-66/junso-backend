package com.zjh.junsobackend;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zjh.junsobackend.pojo.entity.Post;
import com.zjh.junsobackend.service.PostService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * 爬虫测试类
 */
@SpringBootTest
class CrawlerTest {

    @Autowired
    PostService postService;

    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "https://cn.bing.com/images/search?q=" + "鸡你太美" + "&first=" + current;
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        for (Element element : elements) {
            String attr = element.select(".inflnk").get(0).attr("aria-label");
            System.out.println(attr);
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            System.out.println(map.get("murl"));
        }
    }

    @Test
    void crawlerTest() {
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"_score\",\"sortOrder\":\"descend\",\"searchText\":\"\",\"category\":\"文章\",\"reviewStatus\":1}";
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
