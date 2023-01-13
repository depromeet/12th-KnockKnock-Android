package com.depromeet.knockknock.ui.bookmark

sealed class BookmarkNavigationAction {
    object NavigateToBookmarkEdit: BookmarkNavigationAction()
    object NavigateToBookmarkFilterReset: BookmarkNavigationAction()
    object NavigateToBookmarkFilterRoom: BookmarkNavigationAction()
    object NavigateToBookmarkFilterPeriod: BookmarkNavigationAction()
    class NavigateToReaction(val notification_id: Int, val reaction_id: Int) : BookmarkNavigationAction()
    class NavigateToNotificationDetail(val notification_id: Int) : BookmarkNavigationAction()
}