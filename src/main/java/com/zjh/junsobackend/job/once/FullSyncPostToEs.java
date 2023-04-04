package com.zjh.junsobackend.job.once;

import cn.hutool.core.collection.CollectionUtil;
import com.zjh.junsobackend.esdao.PostEsDao;
import com.zjh.junsobackend.pojo.dto.post.PostEsDTO;
import com.zjh.junsobackend.pojo.entity.Post;
import com.zjh.junsobackend.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全量同步的定时方法，只执行一次
 *
 * 实现了CommandLineRunner接口并注入到容器中后每次启动程序都会执行一次run方法
 */
@Component
@Slf4j
public class FullSyncPostToEs implements CommandLineRunner {

    @Resource
    private PostEsDao postEsDao;

    @Resource
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        List<Post> postList = postService.list();

        if (CollectionUtil.isNotEmpty(postList)) {
            List<PostEsDTO> postEsDTOList = postList.stream().map(PostEsDTO::objToDto).collect(Collectors.toList());

            int total = postEsDTOList.size();
            log.info("FullSyncPostToEs start, total {}", total);
            postEsDao.saveAll(postEsDTOList);
            log.info("FullSyncPostToEs start, end {}", total);
        }
    }
}
