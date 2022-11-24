package com.depromeet.knockknock.ui.bookmark

sealed class BookmarkNavigationAction {
    object NavigateToBookmarkEdit: BookmarkNavigationAction()
    object NavigateToBookmarkFilter: BookmarkNavigationAction()
}