package com.depromeet.knockknock.ui.editbookmark

interface EditBookmarkActionHandler {
    fun onCheckClicked(bookmarkIdx: Int, isChecked: Boolean)
    fun onCompleteClicked()
    fun onAllDeleteClicked()
}