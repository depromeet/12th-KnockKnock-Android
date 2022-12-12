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

    override suspend fun refreshTokenAPI(refreshToken: String): NetworkResult<RefreshTokenResponse> {
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

    override suspend fun putUserNickname(nickName: String): NetworkResult<Unit> {
        val body = PutUserNicknameRequest(nickName = nickName)
        return handleApi { mainAPIService.putUserNickname(body = body) }
    }

    override suspend fun getUserNickname(nickName: String): NetworkResult<SearchUserNicknameResponse> {
        val body = GetUserNicknameRequest(nickName = nickName)
        return handleApi { mainAPIService.getUserNickname(body = body).toDomain() }
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

    override suspend fun addGroupMember(id: Int, members: List<Int>): NetworkResult<GroupResponse> {
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
}