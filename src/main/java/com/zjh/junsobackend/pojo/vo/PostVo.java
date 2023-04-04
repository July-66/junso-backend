package com.zjh.junsobackend.pojo.vo;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.zjh.junsobackend.pojo.entity.Post;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PostVo implements Serializable {

    private static final long serialVersionUID = -601042124838752431L;

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private String tags;

    /**
     * VO：转成List后的tags
     */
    private List<String> tagList;

    /**
     * 点赞数
     */
    private Integer thumbnum;

    /**
     * 收藏数
     */
    private Integer favournum;

    /**
     * 创建用户 id
     */
    private Long userid;

    /**
     * VO：用户VO
     */
    private UserVo userVo;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     *VO：当前用户是否点赞了
     */
    private Boolean hasThumb;

    /**
     * VO：当前用户是否收藏了
     */
    private Boolean hasFavour;

    /**
     * Post转为PostVo
     * @param postVo
     * @return
     */
    public static Post voToObj(PostVo postVo) {
        if (postVo == null) {
            return null;
        }
        Post post = new Post();
        BeanUtils.copyProperties(postVo, post);
        List<String> tagList = postVo.getTagList();
        if (tagList != null) {
            post.setTags(JSON.toJSONString(tagList));
        }
        //TODO 补一下hasThumb、hasFavour、UserVO
        return post;
    }

    /**
     * PostVo转为Post
     * @param post
     * @return
     */
    public static PostVo objToVo(Post post) {
        if (post == null) {
            return null;
        }
        PostVo postVo = new PostVo();
        BeanUtils.copyProperties(post, postVo);
        String tags = post.getTags();
        if (tags != null) {
            postVo.setTagList(JSONUtil.parseArray(tags).toList(String.class));
        }
        return postVo;
    }
}
