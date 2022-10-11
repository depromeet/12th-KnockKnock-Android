package com.dida.android.presentation.views.nav.home

sealed class HomeNavigationAction {
    class NavigateToHotItem(val cardId: Long): HomeNavigationAction()
    class NavigateToHotSeller(val userId: Int) : HomeNavigationAction()
    class NavigateToRecentNftItem(val nftId: Int): HomeNavigationAction()
    class NavigateToSoldOut(val cardId: Long): HomeNavigationAction()
    class NavigateToCollection(val userId: Int) : HomeNavigationAction()
}