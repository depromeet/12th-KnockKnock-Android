package com.depromeet.knockknock.ui.bookmark

interface BookmarkActionHandler {
    fun onBookmarkEditClicked()
    fun onFilterResetClicked()
    fun onFilterRoomClicked()
    fun onFilterPeriodClicked()
    fun onReactionClicked(bookmarkIdx: Int)
    fun onNotificationClicked(bookmarkIdx: Int)
}