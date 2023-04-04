package com.zjh.junsobackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjh.junsobackend.common.BaseResponse;
import com.zjh.junsobackend.common.ErrorCode;
import com.zjh.junsobackend.exception.BusinessException;
import com.zjh.junsobackend.pojo.entity.Picture;
import com.zjh.junsobackend.pojo.req.picture.PictureQueryRequest;
import com.zjh.junsobackend.service.PictureService;
import com.zjh.junsobackend.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest) {
        if (pictureQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        int current = pictureQueryRequest.getCurrent();
        long pageSize = pictureQueryRequest.getPageSize();
        String searchText = pictureQueryRequest.getSearchText();
        Page<Picture> page = pictureService.searchPicture(searchText, current, pageSize);
        return ResultUtils.success(page);
    }
}
