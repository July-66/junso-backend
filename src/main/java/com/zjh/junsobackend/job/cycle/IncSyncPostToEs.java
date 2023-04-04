package com.zjh.junsobackend.job.cycle;

import cn.hutool.core.collection.CollectionUtil;
import com.zjh.junsobackend.esdao.PostEsDao;
import com.zjh.junsobackend.mapper.PostMapper;
import com.zjh.junsobackend.pojo.dto.post.PostEsDTO;
import com.zjh.junsobackend.pojo.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 增量同步的定时任务
 *
 * 先将类标注@EnableScheduling，然后在循环任务的方法上用 @Scheduled(fixedRate = 60 * 1000)控制它1分组执行一次
 */
@Component
@Slf4j
@EnableScheduling
public class IncSyncPostToEs {

    @Resource
    private PostEsDao postEsDao;

    @Resource
    private PostMapper postMapper;

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        //获取前五分钟更新的数据，将这个时间设置为执行周期的3-5倍，避免前面几次更新失败，造成数据丢失
        //es中如果数据插入时id一样会覆盖，所以不用担心冲突问题
        Date fiveMinutesAgoDate = new Date(new Date().getTime() - 5 * 60 * 1000L);
        List<Post> postList = postMapper.listPostByUpdateTime(fiveMinutesAgoDate);

        if (CollectionUtil.isNotEmpty(postList)) {
            List<PostEsDTO> postEsDTOList = postList.stream().map(PostEsDTO::objToDto).collect(Collectors.toList());

            int total = postEsDTOList.size();
            log.info("IncSyncPostToEs start, total {}", total);
            postEsDao.saveAll(postEsDTOList);
            log.info("IncSyncPostToEs start, end {}", total);
        }
    }
}






