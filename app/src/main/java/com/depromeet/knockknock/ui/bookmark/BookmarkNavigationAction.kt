package com.depromeet.knockknock.ui.bookmark

sealed class BookmarkNavigationAction {
    object NavigateToBookmarkEdit: BookmarkNavigationAction()
    object NavigateToBookmarkFilterAll: BookmarkNavigationAction()
    object NavigateToBookmarkFilterRoom: BookmarkNavigationAction()
    object NavigateToBookmarkFilterPeriod: BookmarkNavigationAction()
}