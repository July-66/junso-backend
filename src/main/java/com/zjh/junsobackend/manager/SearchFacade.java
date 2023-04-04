package com.zjh.junsobackend.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjh.junsobackend.common.ErrorCode;
import com.zjh.junsobackend.dataSource.DataSource;
import com.zjh.junsobackend.enums.SearchTypeEnum;
import com.zjh.junsobackend.exception.BusinessException;
import com.zjh.junsobackend.pojo.req.search.SearchQueryRequest;
import com.zjh.junsobackend.pojo.vo.SearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class SearchFacade {

    @Autowired
    private DataSourceRegister dataSourceRegister;


    public SearchVo searchAll(@RequestBody SearchQueryRequest searchQueryRequest) {
        String searchText = searchQueryRequest.getSearchText();
        int current = searchQueryRequest.getCurrent();
        long pageSize = searchQueryRequest.getPageSize();
        String type = searchQueryRequest.getType();

        if (StringUtils.isBlank(type)) {
            //为空搜索所有
            return dataSourceRegister.executeAllDoSearch(searchText, current, pageSize);
        } else {
            //不为空根据type搜索，但是如果找不到这个type就抛出异常
            SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByType(type);
            if (searchTypeEnum == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            SearchVo searchVo = new SearchVo();
            DataSource dataSource = dataSourceRegister.getDataSourceByType(type);
            Page page = dataSource.doSearch(searchText, current, pageSize);
            searchVo.setObjectPage(page);
            return searchVo;
        }
    }
}
