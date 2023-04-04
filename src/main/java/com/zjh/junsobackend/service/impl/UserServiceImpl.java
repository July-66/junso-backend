package com.zjh.junsobackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjh.junsobackend.common.ErrorCode;
import com.zjh.junsobackend.constant.CommonConstant;
import com.zjh.junsobackend.exception.BusinessException;
import com.zjh.junsobackend.pojo.entity.User;
import com.zjh.junsobackend.pojo.req.user.UserQueryRequest;
import com.zjh.junsobackend.pojo.vo.UserVo;
import com.zjh.junsobackend.service.UserService;
import com.zjh.junsobackend.mapper.UserMapper;
import com.zjh.junsobackend.util.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 86133
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-03-11 11:29:11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public List<UserVo> getUserVo(List<User> userList) {
        return userList.stream().map(UserVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String unionId = userQueryRequest.getUnionid();
        String mpOpenId = userQueryRequest.getMpopenid();
        String userName = userQueryRequest.getUsername();
        String userProfile = userQueryRequest.getUserprofile();
        String userRole = userQueryRequest.getUserrole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(unionId), "unionId", unionId);
        queryWrapper.eq(StringUtils.isNotBlank(mpOpenId), "mpOpenId", mpOpenId);
        queryWrapper.eq(StringUtils.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.like(StringUtils.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}




