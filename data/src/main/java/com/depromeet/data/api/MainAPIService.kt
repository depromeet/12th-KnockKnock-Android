package com.depromeet.data.api

import com.depromeet.data.model.request.*
import com.depromeet.data.model.response.PagingGroupList
import com.depromeet.data.model.response.PagingNotification
import com.depromeet.data.model.response.PagingNotificationList
import com.depromeet.domain.model.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface MainAPIService {

    // 등록 요청 <- 1번 false일 경우
    @POST("/api/v1/credentials/register")
    suspend fun postRegister(
        @Query("id_token") idToken: String,
        @Query("provider") provider: String,
        @Body body: PostRegisterRequest
    ): LoginResponse

    // 토큰 리프래쉬
    @POST("/api/v1/credentials/refresh")
    suspend fun postRefreshToken(@Body body: PostRefreshTokenRequest): LoginResponse

    // 로그인 요청 <- 가입한 유저 <- 1번 true일 경우
    @POST("/api/v1/credentials/login")
    suspend fun postLogin(@Query("id_token") idToken: String, @Query("provider") provider: String): LoginResponse

    // 토큰 검증 <- 로그인시 제일 처음 1번
    @GET("/api/v1/credentials/oauth/valid/register")
    suspend fun getTokenValidation(@Query("id_token") idToken: String, @Query("provider") provider: String): isRegistedResponse

    // 회원 탈퇴
    @GET("/api/v1/credentials/me")
    suspend fun deleteUer(@Query("oauth_access_token") oauth_access_token: String): Unit

    // 유저 프로필
    @GET("/api/v1/users/profile")
    suspend fun getUserProfile(): UserProfile

    // 유저 프로필 변경
    @PUT("/api/v1/users/profile")
    suspend fun putUserProfile(@Body body: PutUserProfileRequest): UserProfile

    // 닉네임 변경
    @PUT("/api/v1/users/profile")
    suspend fun putUserNickname(@Body body: PutUserNicknameRequest): Unit

    // 내 친구 리스트
    @GET("/api/v1/relations")
    suspend fun getRelations(): FriendList

    // 친구요청
    @POST("/api/v1/relations")
    suspend fun postRelations(@Body body: PostRelationsRequest): Unit

    // 유저 닉네임 검색
    @GET("/api/v1/users/nickname/{nickname}")
    suspend fun getUsersNickname(@Path("nickname") nickname: String): UserList

    // 방 찾기
    @GET("/api/v1/groups/{id}")
    suspend fun getGroup(@Path("id") id: Int): Group

    // 방 검색하기
    @GET("/api/v1/groups/open")
    suspend fun getOpenGroups(
        @Path("searchString") searchString: String,
        @Query("category") category: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): PagingGroupList

    // 방 검색하기 필터링
    @GET("/api/v1/groups/open")
    suspend fun getFilterGroups(
        @Path("type") type: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): PagingGroupList

    // 요즘 뜨는 방
    @GET("/api/v1/groups/famous")
    suspend fun getFamousGroups(): FamousGroupList

    // 그룹 수정
    @PUT("/api/v1/groups/{id}")
    suspend fun putGroup(@Path("id") id: Int, @Body body: PutGroupRequest): Group

    // 그룹 제거
    @DELETE("/api/v1/groups/{id}")
    suspend fun deleteGroup(@Path("id") id: Int): Group

    // 멤버 내쫒기
    @DELETE("/api/v1/groups/{id}/members/{user_id}")
    suspend fun removeMember(@Path("id") id: Int, @Path("user_id") userId: Int): Group

    // 보관함 저장
    @POST("/api/v1/storages/{notification_id}")
    suspend fun postStorages(@Path("notification_id") notification_id: Int): Unit

    // 보관함 조회
    @POST("/api/v1/storages")
    suspend fun getStroages(
        @Query("groupId") groupId: Int,
        @Query("periodOfMonth") periodOfMonth: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): PagingNotification

    // 보관함 삭제
    @DELETE("/api/v1/storages")
    suspend fun deleteStroages(@Body body: DeleteStorageRequest): Unit

    // 리액션 등록
    @POST("/api/v1/reactions")
    suspend fun postReactions(@Body body: PostReactionRequest): Unit

    // 리액션 삭제
    @DELETE("/api/v1/reactions/{notification_reaction_id}")
    suspend fun deleteReaction(@Path("notification_reaction_id") notification_reaction_id: Int): Unit

    // 리액션 수정
    @PATCH("/api/v1/reactions/{notification_reaction_id}")
    suspend fun patchReaction(
        @Path("notification_reaction_id") notification_reaction_id: Int,
        @Body body: PostReactionRequest
    ): Unit

    // 리액션 알림 설정 <- 마이페이지
    @POST("/api/v1/options/reaction")
    suspend fun postOptionReaction(): Unit

    // 리액션 알림 설정 하제  <- 마이페이지
    @DELETE("/api/v1/options/reaction")
    suspend fun deleteOptionReaction(): Unit

    // 야간 푸시알림 설정 <- 마이페이지
    @POST("/api/v1/options/night")
    suspend fun postOptionNight(): Unit

    // 야간 푸시알림 설정하제 <- 마이페이지
    @DELETE("/api/v1/options/night")
    suspend fun deleteOptionNight(): Unit

    // 새로운 푸시알림 설정 <- 마이페이지
    @POST("/api/v1/options/new")
    suspend fun postOptionNew(): Unit

    // 새로운 푸시알림 설정 해제 <- 마이페이지
    @DELETE("/api/v1/options/new")
    suspend fun deleteOptionNew(): Unit

    // 최신 푸쉬 알림 리스트
    @GET("/api/v1/notifications")
    suspend fun getNotifications(): NotificationList

    // 푸쉬 알림 보내기
    @POST("/api/v1/notifications")
    suspend fun postNotifications(@Body body: PostNotificationRequest): Unit

    // FCM 토큰 등록
    @POST("/api/v1/notifications/token")
    suspend fun postNotificationToken(@Body body: PostNotificationTokenRequest): Unit

    // 예약 푸쉬알림 발송
    @POST("/api/v1/notifications/reservation")
    suspend fun postNotificationReservation(@Body body: PostNotificationReservationRequest): Unit

    // 예약 푸쉬알림 시간 수정
    @PATCH("/api/v1/notifications/reservation")
    suspend fun patchNotificationReservation(@Body body: PatchNotificationReservationRequest): Unit

    // 똑똑 미리보기 체험 <- 본인한테 보내기
    @PATCH("/api/v1/notifications/experience")
    suspend fun postNotificationExperience(@Body body: PostNotificationExperienceRequest): Unit

    // 알림방 푸쉬알림 리스트
    @GET("/api/v1/notifications/{group_id}")
    suspend fun getNotification(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String,
        @Path("group_id") group_id: Int
    ): PagingNotificationList

    // 푸쉬 알림 삭제
    @DELETE("/api/v1/notifications/{notification_id}")
    suspend fun deleteNotification(@Path("notification_id") notification_id: Int): Unit

    // 예약 푸쉬 알림 삭제
    @DELETE("/api/v1/notifications/reservation/{reservation_id}")
    suspend fun deleteNotificationReservation(@Path("reservation_id") reservation_id: Int): Unit

    // 파일 URL로 바꾸기
    @POST("/api/v1/images")
    suspend fun postFileToUrl(@Body body: PostFileToUrlRequest): ImageUrl

    // 내 친구목록 멤버 추가
    @POST("/api/v1/groups/{id}/members")
    suspend fun postAddMember(@Path("id") id: Int, @Body body: PostAddMemberRequest): Group

    // 그룹 초대 토큰 검증 & 그룹 가입
    @POST("/api/v1/groups/{id}/members/invite/{code}")
    suspend fun postEnterMembers(@Path("id") id: Int, @Path("code") code: String): Group

    // 그룹 초대 토큰 발급
    @GET("/api/v1/groups/{id}/members/invite")
    suspend fun getGroupToken(@Path("id") id: Int): GroupToken

    // 그룹에서 나가기
    @DELETE("/api/v1/groups/{id}/members/leave")
    suspend fun deleteLeaveGroup(@Path("id") id: Int): Group

    // 그룹 가입 요청 살펴보기
    @GET("/api/v1/groups/{id}/admissions")
    suspend fun getGroupAdmissions(@Path("id") id: Int): Admissions

    // 그룹 가입 요청
    @POST("/api/v1/groups/{id}/admissions")
    suspend fun postGroupAdmissions(@Path("id") id: Int): Admission

    // 그룹 가입 요청 거절
    @POST("/api/v1/groups/{id}/admissions/{admission_id}/refuse")
    suspend fun postGroupAdmissionsRefuse(@Path("id") id: Int, @Path("admission_id") admission_id: Int): Admission

    // 그룹 가입 요청 허락
    @POST("/api/v1/groups/{id}/admissions/{admission_id}/allow")
    suspend fun postGroupAdmissionsAllow(@Path("id") id: Int, @Path("admission_id") admission_id: Int): Admission

    // 공개 그룹 만들기
    @POST("/api/v1/groups/open")
    suspend fun postGroupOpen(@Body body: PostGroupOpenRequest): Group

    // 친구그룹 만들기
    @POST("/api/v1/groups/friend")
    suspend fun postGroupFriend(@Body body: PostGroupFriendRequest): Group

    // 추천 메세지 조회
    @GET("/api/v1/recommendmessage")
    suspend fun getRecommendMessage(): RecommendMessageList

    // 그룹 카테고리
    @GET("/api/v1/groups/categories")
    suspend fun getGroupCategories(): CategoryList

    // 추천 그룹 카테고리
    @GET("/api/v1/groups/categories/famous")
    suspend fun getGroupCategoriesFamous(): CategoryList

    // 앱버젼 체크
    @GET("/api/v1/asset/version")
    suspend fun getAppVersion(): AppVersion

    // 썸네일 이미지
    @GET("/api/v1/asset/thumbnails")
    suspend fun getThumbnails(): ThumbnailList

    // 리액션 이미지
    @GET("/api/v1/asset/reactions")
    suspend fun getReactions(): ReactionList

    // 프로필 이미지
    @GET("/api/v1/asset/profiles")
    suspend fun getProfiles(): ProfileList

    // 프로필 이미지 랜덤
    @GET("/api/v1/asset/profiles/random")
    suspend fun getProfilesRandom(): ProfileList

    // 배경 이미지
    @GET("/api/v1/asset/backgrounds")
    suspend fun getBackgrounds(): BackgroundList

}