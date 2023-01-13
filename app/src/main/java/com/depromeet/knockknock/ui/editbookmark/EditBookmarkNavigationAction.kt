package com.depromeet.knockknock.ui.editbookmark

sealed class EditBookmarkNavigationAction {
    object NavigateToEditDialog: EditBookmarkNavigationAction()
    object NavigateToDeleteComplete: EditBookmarkNavigationAction()
}