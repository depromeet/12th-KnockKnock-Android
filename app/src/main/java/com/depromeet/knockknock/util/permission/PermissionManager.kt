package com.depromeet.knockknock.util.permission

/**
 * https://mishakovalyk.medium.com/an-alternative-approach-to-android-permission-management-3916568777a2
 * https://gist.github.com/mkovalyk/1e80a975a7f7fa9a895c7e6915041009
 */
interface PermissionManager {
    fun unbind()
    fun hasPermission(permission: Permission): Boolean
    fun requestPermission(info: PermissionInfo)
    fun forPermission(type: Permission): PermissionInfo
    fun subscribe(info: PermissionInfo)
}
