package com.depromeet.data.model

import com.depromeet.data.model.response.PagingNotifications
import com.depromeet.data.model.response.PagingGroupList
import com.depromeet.data.model.response.PagingNotificationList
import com.depromeet.domain.model.GroupList
import com.depromeet.domain.model.NotificationContent
import com.depromeet.domain.model.NotificationListContent

fun PagingGroupList.toDomain() : GroupList {
    return GroupList(
        groupContent = groupContent,
        last = last
    )
}

fun PagingNotifications.toDomain() : NotificationContent {
    return NotificationContent(
        content = content,
        last = last
    )
}

fun PagingNotificationList.toDomain() : NotificationListContent {
    return NotificationListContent(
        groups = groups,
        reservations = reservations,
        notifications = notifications,
    )
}