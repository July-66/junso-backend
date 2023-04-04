package com.zjh.junsobackend.pojo.req.picture;

import com.zjh.junsobackend.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 图片搜索请求类
 * @author July
 */
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 2053547363350958666L;

    /**
     * 搜索内容
     */
    private String searchText;
}
