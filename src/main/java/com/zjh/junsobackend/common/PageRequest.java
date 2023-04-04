package com.zjh.junsobackend.common;

import com.zjh.junsobackend.constant.CommonConstant;
import lombok.Data;

@Data
public class PageRequest {

    /**
     * 当前获取的数据的页码
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 20;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}
