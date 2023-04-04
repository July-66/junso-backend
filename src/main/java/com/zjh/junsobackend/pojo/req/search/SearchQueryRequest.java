package com.zjh.junsobackend.pojo.req.search;

import com.zjh.junsobackend.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 7754503301737645184L;

    private String searchText;

    private String type;
}
