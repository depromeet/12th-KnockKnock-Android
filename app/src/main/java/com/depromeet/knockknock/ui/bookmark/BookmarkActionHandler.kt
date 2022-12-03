package com.depromeet.knockknock.ui.bookmark

interface BookmarkActionHandler {
    fun onBookmarkEditClicked()
    fun onFilterAllClicked()
    fun onFilterRoomClicked()
    fun onFilterPeriodClicked()
    fun onReactionClicked(bookmarkIdx: Int)
}