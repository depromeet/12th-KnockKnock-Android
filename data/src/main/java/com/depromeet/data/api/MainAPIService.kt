package com.depromeet.data.api

import com.depromeet.data.model.request.*
import com.depromeet.data.model.response.BaseResponse
import com.depromeet.data.model.response.PagingGroupList
import com.depromeet.data.model.response.PagingNotification
import com.depromeet.data.model.response.PagingNotificationList
import com.depromeet.domain.model.*
import okhttp3.MultipartBody
import retrofit2.http.*


interface MainAPIService {

    // 등록 요청 <- 1번 false일 경우
    @POST("/api/v1/credentials/register")
    suspend fun postRegister(
        @Query("id_token") idToken: String,
        @Query("provider") provider: String,
        @Body body: PostRegisterRequest
    ): BaseResponse<LoginResponse>

    // 토큰 리프래쉬
    @POST("/api/v1/credentials/refresh")
    suspend fun postRefreshToken(@Body body: PostRefreshTokenRequest): BaseResponse<LoginResponse>

    // 회원 탈퇴
    @POST("/api/v1/credentials/logout")
    suspend fun postLogout(): Unit

    // 로그인 요청 <- 가입한 유저 <- 1번 true일 경우
    @POST("/api/v1/credentials/login")
    suspend fun postLogin(
        @Query("id_token") idToken: String,
        @Query("provider") provider: String
    ): BaseResponse<LoginResponse>

    // 토큰 검증 <- 로그인시 제일 처음 1번
    @GET("/api/v1/credentials/oauth/valid/register")
    suspend fun getTokenValidation(
        @Query("id_token") idToken: String,
        @Query("provider") provider: String
    ): BaseResponse<isRegistedResponse>

    // 회원 탈퇴
    @DELETE("/api/v1/credentials/me")
    suspend fun deleteUer(@Query("oauth_access_token") oauth_access_token: String): Unit

    // 유저 프로필
    @GET("/api/v1/users/profile")
    suspend fun getUserProfile(): BaseResponse<UserProfile>

    // 유저 프로필 변경
    @PUT("/api/v1/users/profile")
    suspend fun putUserProfile(@Body body: PutUserProfileRequest): BaseResponse<UserProfile>

    // 닉네임 변경
    @PUT("/api/v1/users/nickname")
    suspend fun putUserNickname(@Body body: PutUserNicknameRequest): Unit

    // 내 친구 리스트
    @GET("/api/v1/relations")
    suspend fun getRelations(): BaseResponse<FriendList>

    // 친구요청
    @POST("/api/v1/relations")
    suspend fun postRelations(@Body body: PostRelationsRequest): Unit

    // 친구삭제
    @DELETE("/api/v1/relations")
    suspend fun deleteRelations(@Body body: PostRelationsRequest): Unit

    // 유저 닉네임 검색
    @GET("/api/v1/users/nickname/{nickname}")
    suspend fun getUsersNickname(@Path("nickname") nickname: String): BaseResponse<UserList>

    // 방 찾기
    @GET("/api/v1/groups/{id}")
    suspend fun getGroup(@Path("id") id: Int): BaseResponse<Group>

    // 방 검색하기
    @GET("/api/v1/groups/open")
    suspend fun getOpenGroups(
        @Query("category") category: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<PagingGroupList>

    // 참여중인 그룹 목록 전체 홀로외침 친구들 방 필터링
    @GET("/api/v1/groups/joined")
    suspend fun getJoinedGroups(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : BaseResponse<PagingGroupList>

    // 방 검색하기 필터링
    @GET("/api/v1/groups/open")
    suspend fun getFilterGroups(
        @Path("type") type: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<PagingGroupList>

    // 요즘 뜨는 방
    @GET("/api/v1/groups/famous")
    suspend fun getFamousGroups(): BaseResponse<FamousGroupList>

    // 그룹 수정
    @PUT("/api/v1/groups/{id}")
    suspend fun putGroup(@Path("id") id: Int, @Body body: PutGroupRequest): BaseResponse<Group>

    // 그룹 제거
    @DELETE("/api/v1/groups/{id}")
    suspend fun deleteGroup(@Path("id") id: Int): BaseResponse<Group>

    // 멤버 내쫒기
    @DELETE("/api/v1/groups/{id}/members/{user_id}")
    suspend fun removeMember(@Path("id") id: Int, @Path("user_id") userId: Int): BaseResponse<Group>

    // 보관함 저장
    @POST("/api/v1/storages/{notification_id}")
    suspend fun postStorages(@Path("notification_id") notification_id: Int): Unit

    // 보관함 조회
    @GET("/api/v1/storages")
    suspend fun getStroages(
        @Body groupId: GetStorageRequest,
        @Query("periodOfMonth") periodOfMonth: Int?,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String?
    ): BaseResponse<PagingNotification>

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

    // 유저의 알림 세팅
    @GET("/api/v1/options")
    suspend fun getOptions(): BaseResponse<Options>

    // 최신 푸쉬 알림 리스트
    @GET("/api/v1/notifications")
    suspend fun getNotifications(): BaseResponse<NotificationList>

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
    @POST("/api/v1/notifications/experience")
    suspend fun postNotificationExperience(@Body body: PostNotificationExperienceRequest): Unit

    // 알림방 푸쉬알림 리스트
    @GET("/api/v1/notifications/{group_id}")
    suspend fun getNotification(
        @Path("group_id") group_id: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): BaseResponse<PagingNotificationList>

    // 푸쉬 알림 삭제
    @DELETE("/api/v1/notifications/{notification_id}")
    suspend fun deleteNotification(@Path("notification_id") notification_id: Int): Unit

    // 예약 푸쉬 알림 삭제
    @DELETE("/api/v1/notifications/reservation/{reservation_id}")
    suspend fun deleteNotificationReservation(@Path("reservation_id") reservation_id: Int): Unit

    // 파일 URL로 바꾸기
    @Multipart
    @POST("/api/v1/images")
    suspend fun postFileToUrl(@Part file: MultipartBody.Part): BaseResponse<ImageUrl>

    // 내 친구목록 멤버 추가
    @POST("/api/v1/groups/{id}/members")
    suspend fun postAddMember(
        @Path("id") id: Int,
        @Body body: PostAddMemberRequest
    ): BaseResponse<Group>

    // 그룹 초대 토큰 검증 & 그룹 가입
    @POST("/api/v1/groups/{id}/members/invite/{code}")
    suspend fun postEnterMembers(
        @Path("id") id: Int,
        @Path("code") code: String
    ): BaseResponse<Group>

    // 그룹 초대 토큰 발급
    @GET("/api/v1/groups/{id}/members/invite")
    suspend fun getGroupToken(@Path("id") id: Int): BaseResponse<GroupToken>

    // 그룹에서 나가기
    @DELETE("/api/v1/groups/{id}/members/leave")
    suspend fun deleteLeaveGroup(@Path("id") id: Int): BaseResponse<Group>

    // 그룹 가입 요청 살펴보기
    @GET("/api/v1/groups/{id}/admissions")
    suspend fun getGroupAdmissions(@Path("id") id: Int): BaseResponse<Admissions>

    // 그룹 가입 요청
    @POST("/api/v1/groups/{id}/admissions")
    suspend fun postGroupAdmissions(@Path("id") id: Int): BaseResponse<Admission>

    // 그룹 가입 요청 거절
    @POST("/api/v1/groups/{id}/admissions/{admission_id}/refuse")
    suspend fun postGroupAdmissionsRefuse(
        @Path("id") id: Int,
        @Path("admission_id") admission_id: Int
    ): BaseResponse<Admission>

    // 그룹 가입 요청 허락
    @POST("/api/v1/groups/{id}/admissions/{admission_id}/allow")
    suspend fun postGroupAdmissionsAllow(
        @Path("id") id: Int,
        @Path("admission_id") admission_id: Int
    ): BaseResponse<Admission>

    // 공개 그룹 만들기
    @POST("/api/v1/groups/open")
    suspend fun postGroupOpen(@Body body: PostGroupOpenRequest): BaseResponse<Group>

    // 친구그룹 만들기
    @POST("/api/v1/groups/friend")
    suspend fun postGroupFriend(@Body body: PostGroupFriendRequest): BaseResponse<Group>

    // 추천 메세지 조회
    @GET("/api/v1/recommendmessage")
    suspend fun getRecommendMessage(): BaseResponse<RecommendMessageList>

    // 그룹 카테고리
    @GET("/api/v1/groups/categories")
    suspend fun getGroupCategories(): BaseResponse<CategoryList>

    // 추천 그룹 카테고리
    @GET("/api/v1/groups/categories/famous")
    suspend fun getGroupCategoriesFamous(): BaseResponse<CategoryList>

    // 앱버젼 체크
    @GET("/api/v1/asset/version")
    suspend fun getAppVersion(): BaseResponse<AppVersion>

    // 썸네일 이미지
    @GET("/api/v1/asset/thumbnails")
    suspend fun getThumbnails(): BaseResponse<ThumbnailList>

    // 리액션 이미지
    @GET("/api/v1/asset/reactions")
    suspend fun getReactions(): BaseResponse<ReactionList>

    // 프로필 이미지
    @GET("/api/v1/asset/profiles")
    suspend fun getProfiles(): BaseResponse<ProfileList>

    // 프로필 이미지 랜덤
    @GET("/api/v1/asset/profiles/random")
    suspend fun getProfilesRandom(): BaseResponse<Profile>

    // 배경 이미지
    @GET("/api/v1/asset/backgrounds")
    suspend fun getBackgrounds(): BaseResponse<BackgroundList>

    // 알림 가져오기
    @GET("/api/v1/alarms")
    suspend fun getAlarms(): BaseResponse<AlarmList>

    // 알림 있는지 확인
    @GET("/api/v1/alarms/count")
    suspend fun getAlarmsCount(): BaseResponse<AlarmCount>

    // 알림 신고하기
    @POST("/api/v1/reports/notifications/{notification_id}")
    suspend fun postReportsNotifications(@Path("notification_id") notification_id: Int, @Body body: PostReportsNotificationRequest): BaseResponse<ReportNotification>

}