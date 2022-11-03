package com.cesc.commonmodel


data class Article(
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val adminAdd: Boolean,
    val host: String,
    val id: Int,
    val isAdminAdd: Boolean,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val prefix: String,
    val origin: String,
    val publishTime: String,
    val projectLink: String,
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<ArticleTag> = emptyList(),

    )

data class ArticleTag(
    val name: String,
    val url: String
)