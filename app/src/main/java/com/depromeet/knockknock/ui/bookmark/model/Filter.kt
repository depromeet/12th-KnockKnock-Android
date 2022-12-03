package com.depromeet.knockknock.ui.bookmark.model

data class Filter(
    val filterIdx: Int,
    val type: FilterType,
    val contents: String,
    var isChecked: Boolean
)

sealed class FilterType {
    object ALL: FilterType()
    object ROOM: FilterType()
    object PERIOD: FilterType()
}