package com.depromeet.knockknock.ui.bookmark

sealed class BookmarkNavigationAction {
    object NavigateToBookmarkEdit: BookmarkNavigationAction()
    object NavigateToBookmarkFilterReset: BookmarkNavigationAction()
    object NavigateToBookmarkFilterRoom: BookmarkNavigationAction()
    object NavigateToBookmarkFilterPeriod: BookmarkNavigationAction()
    class NavigateToReaction(val bookmarkIdx: Int) : BookmarkNavigationAction()
}