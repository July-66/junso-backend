package com.zjh.junsobackend.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjh.junsobackend.constant.CommonConstant;
import com.zjh.junsobackend.esdao.PostEsDao;
import com.zjh.junsobackend.mapper.PostMapper;
import com.zjh.junsobackend.pojo.dto.post.PostEsDTO;
import com.zjh.junsobackend.pojo.entity.Post;
import com.zjh.junsobackend.pojo.entity.User;
import com.zjh.junsobackend.pojo.req.post.PostQueryRequest;
import com.zjh.junsobackend.pojo.vo.PostVo;
import com.zjh.junsobackend.pojo.vo.UserVo;
import com.zjh.junsobackend.service.PostService;
import com.zjh.junsobackend.service.UserService;
import com.zjh.junsobackend.util.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 86133
* @description 针对表【post(帖子)】的数据库操作Service实现
* @createDate 2023-03-10 22:42:17
*/
@Service
@Slf4j
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService{

    @Autowired
    UserService userService;

    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    PostEsDao postEsDao;

    @Override
    public QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        if (postQueryRequest == null) {
            return queryWrapper;
        }
        String searchText = postQueryRequest.getSearchText();
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        Long id = postQueryRequest.getId();
        String title = postQueryRequest.getTitle();
        String content = postQueryRequest.getContent();
        List<String> tagList = postQueryRequest.getTags();
        Long userId = postQueryRequest.getUserId();
        Long notId = postQueryRequest.getNotId();
        // 拼接查询条件
        if (StringUtils.isNotBlank(searchText)) {
            queryWrapper.like("title", searchText).or().like("content", searchText);
        }
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        if (CollectionUtils.isNotEmpty(tagList)) {
            for (String tag : tagList) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public List<PostVo> getPostVO(List<Post> postList) {
        return postList.stream().map(post -> {
            PostVo postVo = PostVo.objToVo(post);
            User user = userService.getById(postVo.getUserid());
            postVo.setUserVo(UserVo.objToVo(user));
            return postVo;
        }).collect(Collectors.toList());
    }

    @Override
    public Page<Post> getFromEs(PostQueryRequest postQueryRequest) {
        if (postQueryRequest == null) {
            return null;
        }
        String searchText = postQueryRequest.getSearchText();
        String title = postQueryRequest.getTitle();
        String content = postQueryRequest.getContent();
        List<String> tags = postQueryRequest.getTags(); //必须全部满足的标签
        List<String> orTags = postQueryRequest.getOrTags(); //只满足其中一个就行的标签
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        int current = postQueryRequest.getCurrent();
        long pageSize = postQueryRequest.getPageSize();

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        //设置should
        if (StringUtils.isNotBlank(title)) {
            queryBuilder.should(QueryBuilders.matchQuery("title", title));
        }

        if (StringUtils.isNotBlank(content)) {
            queryBuilder.should(QueryBuilders.matchQuery("content", content));
        }

        if (StringUtils.isNotBlank(searchText)) {
            queryBuilder.should(QueryBuilders.matchQuery("title", searchText));
            queryBuilder.should(QueryBuilders.matchQuery("content", searchText));
        }

        if (StringUtils.isAllBlank(title, content, searchText)) {
            //如果上面几个查询条件都为空就查询出全部数据
            queryBuilder.should(QueryBuilders.matchAllQuery());
        }
        queryBuilder.minimumShouldMatch(1);//设置should中最少有一个匹配条件

        //设置filter
        queryBuilder.filter(QueryBuilders.termQuery("isDelete", 0));

        if (CollectionUtil.isNotEmpty(tags)) {
            //每一个值设置一个term，就必需列表里所有的标签数据中都存在才能把这条数据查出来
            for (String tag : tags) {
                queryBuilder.filter(QueryBuilders.termQuery("tags", tag));
            }
        }

        if (CollectionUtil.isNotEmpty(orTags)) {
            //terms：只要数据中的tags有一个值存在与查询条件中的tags里，就会被查出来
            queryBuilder.filter(QueryBuilders.termsQuery("tags", orTags));
        }

        //设置分页（es起始页为0！！！！！跟mysql不一样）
        PageRequest pageRequest = PageRequest.of(current - 1, (int) pageSize);


        //设置排序
        //先根据es生成的分数排序
        SortBuilder<?> sortBuilderForScore = SortBuilders.fieldSort("_score");
        sortBuilderForScore.order(SortOrder.DESC);
        //再根据传入的字段进行自定义排序
        SortBuilder<?> sortBuilderForSortField = SortBuilders.scoreSort();
        if (StringUtils.isNotBlank(sortField)) {
            sortBuilderForSortField = SortBuilders.fieldSort(sortField);
            sortBuilderForSortField.order(CommonConstant.SORT_ORDER_ASC.equals(sortOrder) ? SortOrder.ASC : SortOrder.DESC);
        }

        // 构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
                .withPageable(pageRequest).withSorts(sortBuilderForScore, sortBuilderForSortField).build();
        //执行查询，查出来的数据就存在这
        SearchHits<PostEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, PostEsDTO.class);
        List<Post> resultList = new ArrayList<>();

        if (searchHits.hasSearchHits()) {
            //查询数据库
            List<SearchHit<PostEsDTO>> searchHitList = searchHits.getSearchHits();
            List<Long> idList = searchHitList.stream().map(searchHit -> searchHit.getContent().getId()).collect(Collectors.toList());
            //继承的ServiceImpl<PostMapper, Post>中有baseMapper这个成员变量
            List<Post> postList = baseMapper.selectBatchIds(idList);
            Map<Long, Post> postMap = postList.stream().collect(Collectors.toMap(Post::getId, post -> post));
            for (Long id : idList) {
                if (postMap.containsKey(id)) {
                    //数据库中存在该数据，加入结果集
                    resultList.add(postMap.get(id));
                } else {
                    //数据库中不存在该数据，从es中删除
                    String delete = elasticsearchRestTemplate.delete(id.toString(), PostEsDTO.class);
                    log.info("delete post {}", delete);
                }
            }
        }

        Page<Post> page = new Page<>(current, pageSize, searchHits.getTotalHits());
        page.setRecords(resultList);
        return page;
    }
}




