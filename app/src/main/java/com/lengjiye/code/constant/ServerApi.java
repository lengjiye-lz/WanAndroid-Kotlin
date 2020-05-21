package com.lengjiye.code.constant;

public interface ServerApi {

    /**
     * mian
     */
    String HOT_KEY_LIST = "hotkey/json";// 关键字列表


    /**
     * 首页列表
     */
    String ARTICLE = "article/list/{page}/json";
    String ARTICLE_TOP = "article/top/json";
    String BANNER = "banner/json";


    /**
     * 体系
     */
    String TREE = "tree/json";
    String TREE_ARTICLE = "article/list/{page}/json";


    /**
     * 项目
     */
    String PROJECT_TREE = "project/tree/json";
    String PROJECT_TREE_ARTICLE = "project/list/{page}/json";


    /**
     * 登录
     */
    String USER_LOGIN = "user/login";
    String USER_REGISTER = "user/register";
    String USER_LOGOUT = "user/logout/json";


    /**
     * 积分
     */
    String COIN_RANK = "coin/rank/{page}/json";
    String COIN_USER_INFO = "lg/coin/userinfo/json";
    String COIN_USER_INFO_LIST = "lg/coin/list/{page}/json";


    /**
     * 收藏
     */
    String COLLECT_ARTICLE_LIST = "lg/collect/list/{page}/json";
    String COLLECT_ADD_ARTICLE = "lg/collect/{id}/json";
    String COLLECT_ADD_OTHER_ARTICLE = "lg/collect/add/json";
    // 文章列表取消收藏
    String UN_COLLECT_ORIGINID_ARTICLE = "lg/uncollect_originId/{id}/json";
    // 我的收藏列表取消收藏
    String UN_COLLECT_ARTICLE = "lg/uncollect/{id}/json";

    String COLLECT_WEBSITE_LIS = "lg/collect/usertools/json";
    String COLLECT_ADD_WEBSITE = "lg/collect/addtool/json";
    String COLLECT_UPDATE_WEBSITE = "lg/collect/updatetool/json";
    String COLLECT_DELETE_WEBSITE = "lg/collect/deletetool/json";


    /**
     * 广场  分享
     */
    String USER_ARTICLE_LIST = "user_article/list/{page}/json";
    String USER_SHARE_ARTICLES = "user/{user_id}/share_articles/{page}/json";
    String USER_PRIVATE_ARTICLES = "lg/private_articles/{page}/json";
    String USER_ARTICLE_DELETE = "lg/user_article/delete/{article_id}/json";
    String USER_ARTICLE_ADD = "lg/user_article/add/json";

    /**
     * 搜索
     */
    String ARTICLE_QUERY = "article/query/{page}/json";

}
