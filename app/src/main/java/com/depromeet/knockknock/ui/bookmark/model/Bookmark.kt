package com.depromeet.knockknock.ui.bookmark.model

data class Bookmark (
    val bookmarkId: Int,
    val userIdx: Int,
    val userImg: String,
    val userName: String,
    val time: String,
    val contents: String,
    val contentsImg: String? = null,
    val roomName: String,
    val reactionContents: String,
    val reactionCount: Int,
    var isExpanded: Boolean = false
)