package com.example.andy.mvprxretrofit.model.picture;

import java.util.List;

/**
 * <Pre>
 *     美图大全实体类
 * </Pre>
 *
 *
 * @au * @author Westdeco
 * @version 1.0
 *          <p/>
 *          Create by 2017/3/31 15:28
 */
public class ShowApiPictures {
    public PageBean pagebean;
    public String ret_code;
    public class PageBean {
        public String allNum;
        public String allPages;
        public String currentPage;
        public String maxResult;
        public List<PictureBody> contentlist;
    }
}
