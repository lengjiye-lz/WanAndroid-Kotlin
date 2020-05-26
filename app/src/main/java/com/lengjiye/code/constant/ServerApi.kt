package com.lengjiye.code.constant

interface ServerApi {
    companion object {
        /**
         * mian
         */
        const val HOT_KEY_LIST = "hotkey/json" // 关键字列表

        /**
         * 首页列表
         */
        const val ARTICLE = "article/list/{${NetWorkParams.PAGE}}/json"
        const val ARTICLE_TOP = "article/top/json"
        const val BANNER = "banner/json"

        /**
         * 体系
         */
        const val TREE = "tree/json"
        const val TREE_ARTICLE = "article/list/{${NetWorkParams.PAGE}}/json"

        /**
         * 项目
         */
        const val PROJECT_TREE = "project/tree/json"
        const val PROJECT_TREE_ARTICLE = "project/list/{${NetWorkParams.PAGE}}/json"

        /**
         * 登录
         */
        const val USER_LOGIN = "user/login"
        const val USER_REGISTER = "user/register"
        const val USER_LOGOUT = "user/logout/json"

        /**
         * 积分
         */
        const val COIN_RANK = "coin/rank/{${NetWorkParams.PAGE}}/json"
        const val COIN_USER_INFO = "lg/coin/userinfo/json"
        const val COIN_USER_INFO_LIST = "lg/coin/list/{${NetWorkParams.PAGE}}/json"

        /**
         * 收藏
         */
        const val COLLECT_ARTICLE_LIST = "lg/collect/list/{${NetWorkParams.PAGE}}/json"
        const val COLLECT_ADD_ARTICLE = "lg/collect/{${NetWorkParams.ID}}/json"
        const val COLLECT_ADD_OTHER_ARTICLE = "lg/collect/add/json"

        // 文章列表取消收藏
        const val UN_COLLECT_ORIGINID_ARTICLE = "lg/uncollect_originId/{${NetWorkParams.ID}}/json"

        // 我的收藏列表取消收藏
        const val UN_COLLECT_ARTICLE = "lg/uncollect/{${NetWorkParams.ID}}/json"
        const val COLLECT_WEBSITE_LIS = "lg/collect/usertools/json"
        const val COLLECT_ADD_WEBSITE = "lg/collect/addtool/json"
        const val COLLECT_UPDATE_WEBSITE = "lg/collect/updatetool/json"
        const val COLLECT_DELETE_WEBSITE = "lg/collect/deletetool/json"

        /**
         * 广场  分享
         */
        const val USER_ARTICLE_LIST = "user_article/list/{${NetWorkParams.PAGE}}/json"
        const val USER_SHARE_ARTICLES = "user/{user_id}/share_articles/{${NetWorkParams.PAGE}}/json"
        const val USER_PRIVATE_ARTICLES = "lg/private_articles/{${NetWorkParams.PAGE}}/json"
        const val USER_ARTICLE_DELETE = "lg/user_article/delete/{${NetWorkParams.ARTICLE_ID}}/json"
        const val USER_ARTICLE_ADD = "lg/user_article/add/json"

        /**
         * 搜索
         */
        const val ARTICLE_QUERY = "article/query/{${NetWorkParams.PAGE}}/json"

        /**
         * Todo工具
         */
        const val TODO_LIST = "lg/todo/v2/list/{${NetWorkParams.PAGE}}/json"
        const val ADD_TODO = "lg/todo/add/json"
        const val UPDATE_TODO = "lg/todo/update/{${NetWorkParams.ID}}/json"
        const val UPDATE_DONE_TODO = "lg/todo/done/{${NetWorkParams.ID}}/json"
        const val DELETE_TODO = "lg/todo/delete/{${NetWorkParams.ID}}/json"
    }
}