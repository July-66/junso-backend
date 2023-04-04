package com.zjh.junsobackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjh.junsobackend.pojo.entity.Picture;

import java.util.List;

public interface PictureService {

    /**
     * 搜索图片
     *
     * @return 搜索到的图片数据
     */
    Page<Picture> searchPicture(String searchText, int current, long pageSize);
}
