package com.scut.app.bbs.bean;

import java.util.List;

/**
 * 分页查询时的数据类型
 * @author 徐鑫
 */
public class TopicPageData {

    public Integer statusCode;
    public String message;

    public Data data;

    public static class Data {
        public Info info;
    }

    public static class Info {
        public Integer total;
        public List<Topic> list;
        public Integer pageNum;
        public Integer pageSize;
        public Integer size;
        public Integer startRow;
        public Integer endRow;
        public Integer pages;
        public Integer prePage;
        public Integer nextPage;
        public Integer navigatePages;
        public List<Integer> navigatepageNums;
        public Integer navigateFirstPage;
        public Integer navigateLastPage;
        public Boolean isFirstPage;
        public Boolean isLastPage;
        public Boolean hasPreviousPage;
        public Boolean hasNextPage;
    }

}
