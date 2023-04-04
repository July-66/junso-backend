package com.zjh.junsobackend.dataSource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface DataSource<T> {

    Page<T> doSearch(String searchText, int current, long pageSize);
}
