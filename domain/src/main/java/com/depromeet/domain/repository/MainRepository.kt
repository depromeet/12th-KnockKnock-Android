package com.depromeet.domain.repository

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*


interface  MainRepository {

    suspend fun postNotificationToken(deviceId: String, token: String): NetworkResult<Unit>

    suspend fun postRefreshToken(refreshToken: String): NetworkResult<RefreshTokenResponse>

    suspend fun getKakaoLogin(code: String): NetworkResult<KakaoLoginResponse>

    suspend fun getGoogleLogin(code: String): NetworkResult<GoogleLoginResponse>

    suspend fun putUserNickname(nickName: String): NetworkResult<Unit>

    suspend fun getUserNickname(nickName: String): NetworkResult<SearchUserNicknameResponse>

    suspend fun getGroup(id: Int): NetworkResult<GroupResponse>

    suspend fun putGroup(
        id: Int,
        title: String,
        description: String,
        publicAccess: Boolean,
        thumbnailPath: String,
        backgroundImagePath: String,
        categoryId: Int
    ): NetworkResult<GroupResponse>

    suspend fun deleteGroup(id: Int): NetworkResult<Unit>

    suspend fun postAddGroupMember(id: Int, members: List<Int>): NetworkResult<GroupResponse>

    suspend fun getGroupAdmissions(id: Int): NetworkResult<GroupAdmissionsResponse>

    suspend fun postGroupAdmissions(id: Int): NetworkResult<Admissions>

    suspend fun postRefuseGroupAdmissions(id: Int, admissionsId: Int): NetworkResult<Admissions>

    suspend fun postAllowGroupAdmissions(id: Int, admissionsId: Int): NetworkResult<Admissions>

    suspend fun getOpenGroups(category: Int): NetworkResult<OpenGroupsResponse>

    suspend fun postOpenGroups(
        title: String,
        description: String,
        publicAccess: Boolean,
        thumbnailPath: String,
        backgroundImagePath: String,
        categoryId: Int,
        members: List<Int>
    ): NetworkResult<GroupResponse>

    suspend fun postFriendGroups(members: List<Int>): NetworkResult<GroupResponse>

    suspend fun getCategories(): NetworkResult<CategoryResponse>

    suspend fun postCategories(emoji: String, content: String): NetworkResult<Category>

    suspend fun getSearchGroups(searchString: String): NetworkResult<OpenGroupsResponse>

    suspend fun getJoinedGroups(type: String): NetworkResult<OpenGroupsResponse>

    suspend fun deleteGroupMember(id: Int, userId: Int): NetworkResult<GroupResponse>
}