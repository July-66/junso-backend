package com.zjh.junsobackend.dataSource.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjh.junsobackend.dataSource.DataSource;
import com.zjh.junsobackend.pojo.entity.Picture;
import com.zjh.junsobackend.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PictureDataSource implements DataSource<Picture> {
    @Autowired
    PictureService pictureService;

    @Override
    public Page<Picture> doSearch(String searchText, int current, long pageSize) {
        return pictureService.searchPicture(searchText, current, pageSize);
    }
}
