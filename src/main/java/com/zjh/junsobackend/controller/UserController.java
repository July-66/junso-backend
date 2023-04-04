package com.zjh.junsobackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjh.junsobackend.common.BaseResponse;
import com.zjh.junsobackend.common.ErrorCode;
import com.zjh.junsobackend.util.ResultUtils;
import com.zjh.junsobackend.exception.BusinessException;
import com.zjh.junsobackend.pojo.entity.User;
import com.zjh.junsobackend.pojo.req.user.UserQueryRequest;
import com.zjh.junsobackend.pojo.vo.UserVo;
import com.zjh.junsobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取分页UserVO列表
     * @param userQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<UserVo>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        long pageSize = userQueryRequest.getPageSize();
        int current = userQueryRequest.getCurrent();
        Page<User> page = userService.page(new Page<>(current, pageSize), userService.getQueryWrapper(userQueryRequest));
        Page<UserVo> userVoPage = new Page<>(current, pageSize, page.getTotal());
        userVoPage.setRecords(userService.getUserVo(page.getRecords()));
        return ResultUtils.success(userVoPage);
    }
}
