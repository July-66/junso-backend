package com.zjh.junsobackend.pojo.vo;

import com.zjh.junsobackend.pojo.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = -2318627482990740872L;

    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String useraccount;

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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * User转为UserVo
     * @param user
     * @return
     */
    public static UserVo objToVo(User user) {
        if (user == null) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }
}
