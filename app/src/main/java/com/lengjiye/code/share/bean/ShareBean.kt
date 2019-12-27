package com.lengjiye.code.share.bean

import com.lengjiye.code.home.bean.ArticleBean

/**
 * @Author: lz
 * @Date: 2019-12-27
 * @Description: id 是默认属性，不用可以删除，为了防止报错
 */
data class ShareBean(
    val coinInfo: CoinInfo,
    val shareArticles: ArticleBean
)

data class CoinInfo(
    val coinCount: Int, // 371
    val level: Int, // 4
    val rank: Int, // 859
    val userId: Int, // 1448
    val username: String // l**gjiye
)