package com.dida.android.presentation.views.nav.home

interface HomeActionHandler {
    fun onHotItemClicked(cardId: Long)
    fun onHotSellerItemClicked(userId: Int)
    fun onSoldOutItemClicked(cardId: Long)
    fun onCollectionItemClicked(userId: Int)
    fun onSoldOutDayClicked(day: Int)
    fun onUserFollowClicked(userId: Int)
}