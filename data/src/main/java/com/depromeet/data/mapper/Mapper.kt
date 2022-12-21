package com.depromeet.data.mapper

import com.depromeet.data.model.response.*
import com.depromeet.domain.model.*


fun PostRefreshTokenResponse.toDomain() : RefreshTokenResponse {
    return RefreshTokenResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}

fun GetKakaoLoginResponse.toDomain() : KakaoLoginResponse {
    return KakaoLoginResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        isRegisted = this.isRegistered
    )
}

fun GetKakaoLoginLinkResponse.toDomain() : KakaoLoginLinkResponse {
    return KakaoLoginLinkResponse(
        data = this.data,
        status = this.status,
        success = this.success,
        timeStamp = this.timeStamp
    )
}


fun GetGoogleLoginLinkResponse.toDomain() : GoogleLoginLinkResponse {
    return GoogleLoginLinkResponse(
        data = this.data,
        status = this.status,
        success = this.success,
        timeStamp = this.timeStamp
    )
}

fun GetGoogleLoginResponse.toDomain() : GoogleLoginResponse {
    return GoogleLoginResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        isRegisted = this.isRegistered
    )
}

fun PostOauthLoginResponse.toDomain() : OauthLoginResponse {
    return OauthLoginResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}

fun GetUserProfileResponse.toDomain() : UserProfileResponse {
    return UserProfileResponse(
        id = id,
        nickname = nickname,
        profilePath = profilePath,
        email = email
    )
}

fun GetUserNicknameResponse.toDomain() : SearchUserNicknameResponse {
    return SearchUserNicknameResponse(
        userList = this.userList
    )
}

fun GetFriendListResponse.toDomain() : FriendListResponse {
    return FriendListResponse(
        friendList = friendList
    )
}

fun GetSearchUserResponse.toDomain() : UserListResponse {
    return UserListResponse(
        userList = userList
    )
}

fun GetGroupResponse.toDomain() : GroupResponse {
    return GroupResponse(
        groupId = this.groupId,
        title = this.title,
        description = this.description,
        thumbnailPath = this.thumbnailPath,
        backgroundImagePath = this.backgroundImagePath,
        publicAccess = this.publicAccess,
        category = this.category,
        members = this.members,
        groupType = this.groupType,
        host = host
    )
}

fun GetGroupAdmissionsResponse.toDomain() : GroupAdmissionsResponse {
    return GroupAdmissionsResponse(
        admissions = this.admissions
    )
}

fun GetOpenGroupsResponse.toDomain() : OpenGroupsResponse {
    return OpenGroupsResponse(
        groupInfos = this.groupInfos
    )
}

fun GetCategoriesResponse.toDomain() : CategoryResponse {
    return CategoryResponse(
        categories = this.categories
    )
}


