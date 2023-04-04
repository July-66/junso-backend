package com.zjh.junsobackend.controller;

import com.zjh.junsobackend.common.BaseResponse;
import com.zjh.junsobackend.manager.SearchFacade;
import com.zjh.junsobackend.pojo.req.search.SearchQueryRequest;
import com.zjh.junsobackend.pojo.vo.SearchVo;
import com.zjh.junsobackend.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchFacade searchFacade;//门面模式：根据前端传入的type返回前端需要的数据

    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchQueryRequest searchQueryRequest) {
        return ResultUtils.success(searchFacade.searchAll(searchQueryRequest));
    }
}
