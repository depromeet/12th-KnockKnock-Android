package com.depromeet.domain.repository

import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import okhttp3.MultipartBody

interface  MainRepository {

    // 등록 요청
    suspend fun postRegister(
        idToken: String,
        provider: String,
        nickname: String,
        profile_path: String
    ): NetworkResult<LoginResponse>

    // 토큰 리프래쉬
    suspend fun postRefreshToken(refresh_token: String): NetworkResult<LoginResponse>

    // 로그인 요청 <- 가입한 유저
    suspend fun postLogin(idToken: String,  provider: String): NetworkResult<LoginResponse>

    // 토큰 검증
    suspend fun getTokenValidation(idToken: String, provider: String): NetworkResult<isRegistedResponse>

    // 회원 탈퇴
    suspend fun deleteUer(oauth_access_token: String): NetworkResult<Unit>

    // 유저 프로필
    suspend fun getUserProfile(): NetworkResult<UserProfile>

    // 유저 프로필 변경
    suspend fun putUserProfile(nickname: String, profile_path: String): NetworkResult<UserProfile>

    // 닉네임 변경
    suspend fun putUserNickname(nickname: String): NetworkResult<Unit>

    // 내 친구 리스트
    suspend fun getRelations(): NetworkResult<FriendList>

    // 친구요청
    suspend fun postRelations(user_id: Int): NetworkResult<Unit>

    // 유저 닉네임 검색
    suspend fun getUsersNickname(nickname: String): NetworkResult<UserList>

    // 방 찾기
    suspend fun getGroup(id: Int): NetworkResult<Group>

    // 방 검색하기
    suspend fun getOpenGroups(
        searchString: String,
        category: Int,
        page: Int,
        size: Int
    ): NetworkResult<GroupList>

    // 방 검색하기 필터링
    suspend fun getFilterGroups(
        type: String,
        page: Int,
        size: Int
    ): NetworkResult<GroupList>

    // 요즘 뜨는 방
    suspend fun getFamousGroups(): NetworkResult<FamousGroupList>

    // 그룹 수정
    suspend fun putGroup(
        id: Int,
        title: String,
        description: String,
        public_access: Boolean,
        thumbnail_path: String,
        background_image_path: String,
        category_id: Int
    ): NetworkResult<Group>

    // 그룹 제거
    suspend fun deleteGroup(id: Int): NetworkResult<Group>

    // 멤버 내쫒기
    suspend fun removeMember(id: Int, userId: Int): NetworkResult<Group>

    // 보관함 저장
    suspend fun postStorages(notification_id: Int): NetworkResult<Unit>

    // 보관함 조회
    suspend fun getStroages(
        groupId: Int,
        periodOfMonth: Int,
        page: Int,
        size: Int,
        sort: String
    ): NetworkResult<NotificationContent>

    // 보관함 삭제
    suspend fun deleteStroages(storage_ids: List<Int>): NetworkResult<Unit>

    // 리액션 등록
    suspend fun postReactions(notification_id: Int, reaction_id: Int): NetworkResult<Unit>

    // 리액션 삭제
    suspend fun deleteReaction(notification_reaction_id: Int): NetworkResult<Unit>

    // 리액션 수정
    suspend fun patchReaction(
        notification_reaction_id: Int,
        notification_id: Int,
        reaction_id: Int
    ): NetworkResult<Unit>

    // 리액션 알림 설정 <- 마이페이지
    suspend fun postOptionReaction(): NetworkResult<Unit>

    // 리액션 알림 설정 하제  <- 마이페이지
    suspend fun deleteOptionReaction(): NetworkResult<Unit>

    // 야간 푸시알림 설정 <- 마이페이지
    suspend fun postOptionNight(): NetworkResult<Unit>

    // 야간 푸시알림 설정하제 <- 마이페이지
    suspend fun deleteOptionNight(): NetworkResult<Unit>

    // 새로운 푸시알림 설정 <- 마이페이지
    suspend fun postOptionNew(): NetworkResult<Unit>

    // 새로운 푸시알림 설정 해제 <- 마이페이지
    suspend fun deleteOptionNew(): NetworkResult<Unit>

    // 최신 푸쉬 알림 리스트
    suspend fun getNotifications(): NetworkResult<NotificationList>

    // 푸쉬 알림 보내기
    suspend fun postNotifications(
        group_id: Int,
        title: String,
        content: String,
        image_url: String
    ): NetworkResult<Unit>

    // FCM 토큰 등록
    suspend fun postNotificationToken(device_id: String, token: String): NetworkResult<Unit>

    // 예약 푸쉬알림 발송
    suspend fun postNotificationReservation(
        group_id: Int,
        title: String,
        content: String,
        image_url: String,
        send_at: String
    ): NetworkResult<Unit>

    // 예약 푸쉬알림 시간 수정
    suspend fun patchNotificationReservation(
        reservation_id: Int,
        send_at: String
    ): NetworkResult<Unit>

    // 똑똑 미리보기 체험 <- 본인한테 보내기
    suspend fun postNotificationExperience(
        token: String,
        content: String
    ): NetworkResult<Unit>

    // 알림방 푸쉬알림 리스트
    suspend fun getNotification(
        page: Int,
        size: Int,
        sort: String,
        group_id: Int
    ): NetworkResult<NotificationContent>

    // 푸쉬 알림 삭제
    suspend fun deleteNotification(notification_id: Int): NetworkResult<Unit>

    // 예약 푸쉬 알림 삭제
    suspend fun deleteNotificationReservation(reservation_id: Int): NetworkResult<Unit>

    // 파일 URL로 바꾸기
    suspend fun postFileToUrl(
        file : MultipartBody.Part
    ): NetworkResult<ImageUrl>

    // 내 친구목록 멤버 추가
    suspend fun postAddMember(id: Int, member_ids: List<Int>): NetworkResult<Group>

    // 그룹 초대 토큰 검증 & 그룹 가입
    suspend fun postEnterMembers(id: Int,code: String): NetworkResult<Group>

    // 그룹 초대 토큰 발급
    suspend fun getGroupToken(id: Int): NetworkResult<GroupToken>

    // 그룹에서 나가기
    suspend fun deleteLeaveGroup(id: Int): NetworkResult<Group>

    // 그룹 가입 요청 살펴보기
    suspend fun getGroupAdmissions(id: Int): NetworkResult<Admissions>

    // 그룹 가입 요청
    suspend fun postGroupAdmissions(id: Int): NetworkResult<Admission>

    // 그룹 가입 요청 거절
    suspend fun postGroupAdmissionsRefuse(id: Int,admission_id: Int): NetworkResult<Admission>

    // 그룹 가입 요청 허락
    suspend fun postGroupAdmissionsAllow(id: Int,admission_id: Int): NetworkResult<Admission>

    // 공개 그룹 만들기
    suspend fun postGroupOpen(
        title: String,
        description: String,
        public_access: Boolean,
        thumbnail_path: String,
        background_image_path: String,
        category_id: Int,
        member_ids: List<Int>
    ): NetworkResult<Group>

    // 친구그룹 만들기
    suspend fun postGroupFriend(member_ids: List<Int>): NetworkResult<Group>

    // 추천 메세지 조회
    suspend fun getRecommendMessage(): NetworkResult<RecommendMessageList>

    // 그룹 카테고리
    suspend fun getGroupCategories(): NetworkResult<CategoryList>

    // 추천 그룹 카테고리
    suspend fun getGroupCategoriesFamous(): NetworkResult<CategoryList>

    // 앱버젼 체크
    suspend fun getAppVersion(): NetworkResult<AppVersion>

    // 썸네일 이미지
    suspend fun getThumbnails(): NetworkResult<ThumbnailList>

    // 리액션 이미지
    suspend fun getReactions(): NetworkResult<ReactionList>

    // 프로필 이미지
    suspend fun getProfiles(): NetworkResult<ProfileList>

    // 프로필 이미지 랜덤
    suspend fun getProfilesRandom(): NetworkResult<Profile>

    // 배경 이미지
    suspend fun getBackgrounds(): NetworkResult<BackgroundList>
}