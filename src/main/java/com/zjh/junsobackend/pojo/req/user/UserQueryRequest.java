package com.zjh.junsobackend.pojo.req.user;

import com.zjh.junsobackend.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -1132373282809553723L;

    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String useraccount;

    /**
     * 微信开放平台id
     */
    private String unionid;

    /**
     * 公众号openId
     */
    private String mpopenid;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户头像
     */
    private String useravatar;

    /**
     * 用户简介
     */
    private String userprofile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userrole;
}
