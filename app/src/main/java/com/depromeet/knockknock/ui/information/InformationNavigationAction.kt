package com.depromeet.knockknock.ui.information

sealed class InformationNavigationAction {
    object NavigateToUserConsents: InformationNavigationAction()
    object NavigateToUserPrivacy: InformationNavigationAction()
    object NavigateToAppMakers: InformationNavigationAction()
}