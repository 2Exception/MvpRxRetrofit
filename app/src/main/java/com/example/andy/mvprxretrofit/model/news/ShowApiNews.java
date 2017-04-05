package com.example.andy.mvprxretrofit.model.news;

import java.util.List;

/**
 * <Pre>
 *     新闻列表实体类
 * </Pre>
 *
 * @author Westdeco
 * @version 1.0
 *          <p/>
 *          Create by 2017/3/31 15:28
 */
public class ShowApiNews {
    public PageBean pagebean;
    public String ret_code;
    public class PageBean {
        public String allNum;
        public String allPages;
        public String currentPage;
        public String maxResult;
        public List<NewsBody> contentlist;
    }
}
