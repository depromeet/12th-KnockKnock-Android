package com.depromeet.data.api

import com.depromeet.data.model.request.*
import com.depromeet.data.model.response.*
import com.depromeet.domain.model.Admissions
import com.depromeet.domain.model.Category
import com.depromeet.domain.model.CategoryResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface MainAPIService {

    // FCM 디바이스 토큰 관련
    @POST("/notifications/token")
    suspend fun postNotificationToken(@Body body: PostNotifcationTokenRequest): Unit

    // Refresh Token 재발급
    @POST("/api/v1/credentials/refresh")
    suspend fun postRefreshToken(@Body body: PostRefreshTokenRequest): PostRefreshTokenResponse

    // Oauth Kakao 로그인
    @GET("/api/v1/credentials/oauth/link/kakao")
    suspend fun getKakaoLogin(@Body body: GetKakaoLoginRequest): GetKakaoLoginResponse

    // Oauth Google 로그인
    @GET("/api/v1/credentials/oauth/link/google")
    suspend fun getGoogleLogin(@Body body: GetGoogleLoginRequest): GetGoogleLoginResponse

    // 유저 프로필 가져오기
    @GET("/api/v1/users/profile")
    suspend fun getUserProfile(): GetUserProfileResponse

    // 유저 프로필 변경하기
    @PUT("/api/v1/users/profile")
    suspend fun putUserProfile(@Body body: PutUserProfileRequest): GetUserProfileResponse

    // 유저 닉네임 변경
    @PUT("/api/v1/users/nickname")
    suspend fun putUserNickname(@Body body: PutUserNicknameRequest): Unit

    // 유저 닉네임 검색
    @GET("/users/nickname")
    suspend fun getUserNickname(@Body body: GetUserNicknameRequest): GetUserNicknameResponse

    // 내 친구 리스트 가져오기
    @GET("/api/v1/relations")
    suspend fun getFriendList(): GetFriendListResponse

    // 친구 요청 보내기
    @POST("/api/v1/relations")
    suspend fun postFriend(@Body body: PostFriendRequest): Unit

    // 유저 닉네임 검색
    @GET("/api/v1/users/nickname/{nickname}")
    suspend fun getSearchUser(@Path("nickname") nickname: String): GetSearchUserResponse

    // 그룹 정보
    @GET("/api/v1/groups/{id}")
    suspend fun getGroup(@Path("id") id: Int): GetGroupResponse

    // 그룹 찾기 <- 미적용
    @GET("/api/v1/groups/open")
    suspend fun getSearchGroup(
        @Query("category") category: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    )

    // 그룹 검색 <- 미적용
    @GET("/api/v1/groups/search/{searchString}")
    suspend fun getSearchGroupKeyword(
        @Path("searchString") searchString: String,
        @Query("category") category: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    )

    // 참여중인 그룹 필터링 <- 미적용
    @GET("/api/v1/groups/joined")
    suspend fun getJoinedGroupFilter(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    )

    // 요즘 뜨는 그룹 <- 미적용
    @GET("/api/v1/groups/famous")
    suspend fun getHotGroups()

    // 그룹 설정 변경(방장 권한)
    @PUT("/groups/{id}")
    suspend fun putGroup(@Path("id") id: Int, @Body body: PutGroupRequest): GetGroupResponse

    // 그룹 설정 제거(방장 권한)
    @DELETE("/groups/{id}")
    suspend fun deleteGroup(@Path("id") id: Int): Unit

    // 그룹 설정 멤버 제거(방장 권한) <- 미적용
    @DELETE("/groups/{id}")
    suspend fun deleteUserInGroup(@Path("id") id: Int, @Path("user_id") userId: Int)

    // 그룹 멤버 추가(방장 권한)
    @POST("/groups/{id}/members")
    suspend fun postAddGroupMember(@Path("id") id: Int, @Body body: PostAddGroupMemberRequest): GetGroupResponse

    // 그룹 가입 요청 살펴보기(방장 권한)
    @GET("/groups/{id}/admissions")
    suspend fun getGroupAdmissions(@Path("id") id: Int): GetGroupAdmissionsResponse

    // 그룹 가입 요청
    @POST("/groups/{id}/admissions")
    suspend fun postGroupAdmissions(@Path("id") id: Int): Admissions

    // 그룹 가입 거절(방장 권한)
    @POST("/groups/{id}/admissions/{admission_id}/refuse")
    suspend fun postRefuseGroupAdmissions(@Path("id") id: Int, @Path("admission_id") admissionId: Int): Admissions

    // 그룹 가입 허용(방장 권한)
    @POST("/groups/{id}/admissions/{admission_id}/allow")
    suspend fun postAllowGroupAdmissions(@Path("id") id: Int, @Path("admission_id") admissionId: Int): Admissions

    // 방 찾기
    @GET("/groups/open")
    suspend fun getOpenGroups(@Query("category") category: Int): GetOpenGroupsResponse

    // 공개 그룹 만들기
    @POST("/groups/open")
    suspend fun postOpenGroups(@Body body: PostOpenGroupRequest): GetGroupResponse

    // 친구 그룹 만들기
    @POST("/groups/friend")
    suspend fun postFriendGroups(@Body body: PostFriendGroupRequest): GetGroupResponse

    // 카테고리 목록
    @GET("/groups/categories")
    suspend fun getCategories(): GetCategoriesResponse

    // 카테고리 생성
    @POST("/groups/categories")
    suspend fun postCategories(@Body body: PostCategoryRequest): Category

    // 방 검색하기
    @GET("/groups/search/{searchString}")
    suspend fun getSearchGroups(@Path("searchString") searchString: String): GetOpenGroupsResponse

    // 참여중인 그룹 필터링 (전체, 홀로, 친구들)
    @GET("/groups/joined")
    suspend fun getJoinedGroups(@Query("type") type: String): GetOpenGroupsResponse

    // 멤버 제거 (방장 : 본인제외 모든 인원, 멤버 : 본인만) <- 방 나가기 처럼 사용
    @DELETE("/groups/{id}/members/{user_id}")
    suspend fun deleteGroupMember(@Path("id") id: Int, @Path("user_id") userId: Int): GetGroupResponse

}