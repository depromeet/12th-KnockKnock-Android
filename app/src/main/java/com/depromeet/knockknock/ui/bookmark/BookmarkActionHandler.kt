package com.depromeet.knockknock.ui.bookmark

interface BookmarkActionHandler {
    fun onBookmarkEditClicked()
    fun onFilterResetClicked()
    fun onFilterRoomClicked()
    fun onFilterPeriodClicked()
    fun onReactionClicked(notification_id: Int, reaction_id: Int)
    fun onNotificationClicked(bookmarkIdx: Int)
}