package com.depromeet.knockknock.ui.editbookmark.model

data class EditBookmark (
    val bookmarkId: Int,
    val userIdx: Int,
    val userImg: String,
    val userName: String,
    val dateTime: String,
    val contents: String,
    val contentsImg: String? = null,
    var isChecked: Boolean = false
)