package com.zjh.junsobackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjh.junsobackend.common.ErrorCode;
import com.zjh.junsobackend.constant.CommonConstant;
import com.zjh.junsobackend.exception.BusinessException;
import com.zjh.junsobackend.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjh.junsobackend.pojo.req.user.UserQueryRequest;
import com.zjh.junsobackend.pojo.vo.UserVo;
import com.zjh.junsobackend.util.SqlUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
* @author 86133
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-03-11 11:29:11
*/
public interface UserService extends IService<User> {

    /**
     * userList转userVoList
     * @param userList
     * @return userList对应的UserVoList
     */
    List<UserVo> getUserVo(List<User> userList);


    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
