package com.zjh.junsobackend.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjh.junsobackend.common.ErrorCode;
import com.zjh.junsobackend.exception.BusinessException;
import com.zjh.junsobackend.pojo.entity.Picture;
import com.zjh.junsobackend.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PictureServiceImpl implements PictureService {

    @Override
    public Page<Picture> searchPicture(String searchText, int current, long pageSize) {
        int firstIndex = (int)((current - 1) * pageSize);
        String url = "https://cn.bing.com/images/search?q=" + searchText + "&first=" + firstIndex;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取页面结构失败");
        }
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        String title;
        String m;
        for (Element element : elements) {
            if (pictureList.size() >= pageSize) {
                break;
            }
            title = element.select(".inflnk").get(0).attr("aria-label");
            m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(map.get("murl").toString());
            pictureList.add(picture);
        }
        Page<Picture> page = new Page<>(current, pageSize);
        page.setRecords(pictureList);
        return page;
    }
}
