package com.depromeet.knockknock.util.defaultimage

import com.depromeet.domain.model.Profile

interface DefaultImageActionHandler {
    fun onDefaultImageClicked(profile: Profile)
}