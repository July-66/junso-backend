package com.zjh.junsobackend.pojo.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjh.junsobackend.common.PageRequest;
import com.zjh.junsobackend.pojo.entity.Picture;
import lombok.Data;

@Data
public class SearchVo {
    /**
     * 图片
     */
    private Page<Picture> picturePage;

    /**
     * 用户
     */
    private Page<UserVo> userVoPage;

    /**
     * 推文
     */
    private Page<PostVo> postVoPage;

    /**
     * 通用返回值
     */
    private Page<Object> objectPage;
}
