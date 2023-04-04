package com.zjh.junsobackend.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 图片类
 *
 *
 * @author July
 */

@Data
public class Picture implements Serializable {

    private static final long serialVersionUID = 7111032660439645127L;

    /**
     * 图片标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String url;
}
