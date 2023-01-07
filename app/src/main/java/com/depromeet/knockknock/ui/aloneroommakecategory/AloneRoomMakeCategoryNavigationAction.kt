package com.depromeet.knockknock.ui.aloneroommakecategory

import com.depromeet.domain.model.Background
import com.depromeet.domain.model.Group
import com.depromeet.domain.model.Thumbnail


sealed class AloneRoomMakeCategoryNavigationAction {
    class NavigateToAloneRoomDetails(
        val categoryId: Int? = null
    ) : AloneRoomMakeCategoryNavigationAction()
}