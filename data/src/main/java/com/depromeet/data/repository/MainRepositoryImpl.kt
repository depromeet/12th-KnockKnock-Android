package com.depromeet.data.repository

import com.depromeet.data.api.MainAPIService
import com.depromeet.data.api.handleApi
import com.depromeet.data.mapper.toDomain
import com.depromeet.data.model.request.*
import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import com.depromeet.domain.repository.MainRepository
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
): MainRepository {

    override suspend fun postNotificationToken(deviceId: String, token: String): NetworkResult<Unit> {
        val body = PostNotifcationTokenRequest(
            deviceId = deviceId,
            token = token
        )
        return handleApi { mainAPIService.postNotificationToken(body) }
    }

    override suspend fun postRefreshToken(refreshToken: String): NetworkResult<RefreshTokenResponse> {
        val body = PostRefreshTokenRequest(refreshToken = refreshToken)
        return handleApi { mainAPIService.postRefreshToken(body = body).toDomain() }
    }

    override suspend fun getKakaoLogin(code: String): NetworkResult<KakaoLoginResponse> {
        val body = GetKakaoLoginRequest(code = code)
        return handleApi { mainAPIService.getKakaoLogin(body = body).toDomain() }
    }

    override suspend fun getGoogleLogin(code: String): NetworkResult<GoogleLoginResponse> {
        val body = GetGoogleLoginRequest(code = code)
        return handleApi { mainAPIService.getGoogleLogin(body = body).toDomain() }
    }

    override suspend fun postOauthLogin(
        idToken: String,
        provider: String
    ): NetworkResult<OauthLoginResponse> {
        return handleApi { mainAPIService.postOauthLogin(idToken = idToken, provider = provider).toDomain() }
    }

    override suspend fun getUserProfile(): NetworkResult<UserProfileResponse> {
        return handleApi { mainAPIService.getUserProfile().toDomain() }
    }

    override suspend fun putUserProfile(
        nickName: String,
        profilePath: String
    ): NetworkResult<UserProfileResponse> {
        val body = PutUserProfileRequest(nickname = nickName, profilePath = profilePath)
        return handleApi { mainAPIService.putUserProfile(body = body).toDomain() }
    }

    override suspend fun putUserNickname(nickName: String): NetworkResult<Unit> {
        val body = PutUserNicknameRequest(nickName = nickName)
        return handleApi { mainAPIService.putUserNickname(body = body) }
    }

    override suspend fun getUserNickname(nickName: String): NetworkResult<SearchUserNicknameResponse> {
        val body = GetUserNicknameRequest(nickName = nickName)
        return handleApi { mainAPIService.getUserNickname(body = body).toDomain() }
    }

    override suspend fun getFriendList(): NetworkResult<FriendListResponse> {
        return handleApi { mainAPIService.getFriendList().toDomain() }
    }

    override suspend fun postFriend(userId: Int): NetworkResult<Unit> {
        val body = PostFriendRequest(userId = userId)
        return handleApi { mainAPIService.postFriend(body) }
    }

    override suspend fun getSearchUser(nickName: String): NetworkResult<UserListResponse> {
        return handleApi { mainAPIService.getSearchUser(nickname = nickName).toDomain() }
    }

    override suspend fun getGroup(id: Int): NetworkResult<GroupResponse> {
        return handleApi { mainAPIService.getGroup(id = id).toDomain() }
    }

    override suspend fun putGroup(
        id: Int,
        title: String,
        description: String,
        publicAccess: Boolean,
        thumbnailPath: String,
        backgroundImagePath: String,
        categoryId: Int
    ): NetworkResult<GroupResponse> {
        val body = PutGroupRequest(
            title = title,
            description = description,
            publicAccess = publicAccess,
            thumbnailPath = thumbnailPath,
            backgroundImagePath = backgroundImagePath,
            categoryId = categoryId
        )

        return handleApi { mainAPIService.putGroup(id = id, body = body).toDomain() }
    }

    override suspend fun deleteGroup(id: Int): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteGroup(id = id) }
    }

    override suspend fun postAddGroupMember(id: Int, members: List<Int>): NetworkResult<GroupResponse> {
        val body = PostAddGroupMemberRequest(memberIds = members)
        return handleApi { mainAPIService.postAddGroupMember(id = id, body = body).toDomain() }
    }

    override suspend fun getGroupAdmissions(id: Int): NetworkResult<GroupAdmissionsResponse> {
        return handleApi { mainAPIService.getGroupAdmissions(id = id).toDomain() }
    }

    override suspend fun postGroupAdmissions(id: Int): NetworkResult<Admissions> {
        return handleApi { mainAPIService.postGroupAdmissions(id = id) }
    }

    override suspend fun postRefuseGroupAdmissions(
        id: Int,
        admissionsId: Int
    ): NetworkResult<Admissions> {
        return handleApi { mainAPIService.postRefuseGroupAdmissions(id = id, admissionId = admissionsId) }
    }

    override suspend fun postAllowGroupAdmissions(
        id: Int,
        admissionsId: Int
    ): NetworkResult<Admissions> {
        return handleApi { mainAPIService.postAllowGroupAdmissions(id = id, admissionId = admissionsId) }
    }

    override suspend fun getOpenGroups(category: Int): NetworkResult<OpenGroupsResponse> {
        return handleApi { mainAPIService.getOpenGroups(category = category).toDomain() }
    }

    override suspend fun postOpenGroups(
        title: String,
        description: String,
        publicAccess: Boolean,
        thumbnailPath: String,
        backgroundImagePath: String,
        categoryId: Int,
        members: List<Int>
    ): NetworkResult<GroupResponse> {
        val body = PostOpenGroupRequest(
            title = title,
            description = description,
            publicAccess = publicAccess,
            thumbnailPath = thumbnailPath,
            backgroundImagePath = backgroundImagePath,
            categoryId = categoryId,
            memberIds = members
        )
        return handleApi { mainAPIService.postOpenGroups(body).toDomain() }
    }

    override suspend fun postFriendGroups(members: List<Int>): NetworkResult<GroupResponse> {
        val body = PostFriendGroupRequest(memberIds = members)
        return handleApi { mainAPIService.postFriendGroups(body).toDomain() }
    }

    override suspend fun getCategories(): NetworkResult<CategoryResponse> {
        return handleApi { mainAPIService.getCategories().toDomain() }
    }

    override suspend fun postCategories(emoji: String, content: String): NetworkResult<Category> {
        val body = PostCategoryRequest(emoji = emoji, content = content)
        return handleApi { mainAPIService.postCategories(body) }
    }

    override suspend fun getSearchGroups(searchString: String): NetworkResult<OpenGroupsResponse> {
        return handleApi { mainAPIService.getSearchGroups(searchString = searchString).toDomain() }
    }

    override suspend fun getJoinedGroups(type: String): NetworkResult<OpenGroupsResponse> {
        val filterType = when(type) {
            "FRIEND" -> "FRIEND"
            "OPEN" -> "OPEN"
            else -> "ALL"
        }
        return handleApi { mainAPIService.getJoinedGroups(filterType).toDomain() }
    }

    override suspend fun deleteGroupMember(id: Int, userId: Int): NetworkResult<GroupResponse> {
        return handleApi { mainAPIService.deleteGroupMember(id = id, userId = userId).toDomain() }
    }
}