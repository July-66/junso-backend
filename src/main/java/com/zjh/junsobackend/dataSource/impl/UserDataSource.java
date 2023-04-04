package com.zjh.junsobackend.dataSource.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjh.junsobackend.dataSource.DataSource;
import com.zjh.junsobackend.pojo.entity.User;
import com.zjh.junsobackend.pojo.req.user.UserQueryRequest;
import com.zjh.junsobackend.pojo.vo.UserVo;
import com.zjh.junsobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDataSource implements DataSource<UserVo> {
    @Autowired
    UserService userService;

    @Override
    public Page<UserVo> doSearch(String searchText, int current, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUsername(searchText);
        userQueryRequest.setCurrent(current);
        userQueryRequest.setPageSize(pageSize);
        Page<User> userPage = userService.page(new Page<>(current, pageSize), userService.getQueryWrapper(userQueryRequest));
        Page<UserVo> userVoPage = new Page<>(current, pageSize, userPage.getTotal());
        userVoPage.setRecords(userService.getUserVo(userPage.getRecords()));
        return userVoPage;
    }
}
