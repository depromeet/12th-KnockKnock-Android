package com.depromeet.data.repository

import com.depromeet.data.api.MainAPIService
import com.depromeet.data.api.handleApi
import com.depromeet.data.model.request.*
import com.depromeet.data.model.toDomain
import com.depromeet.domain.NetworkResult
import com.depromeet.domain.model.*
import com.depromeet.domain.repository.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named

class MainRepositoryImpl @Inject constructor(
    @Named("Main") private val mainAPIService: MainAPIService
): MainRepository {

    override suspend fun postRegister(
        idToken: String,
        provider: String,
        nickname: String,
        profile_path: String
    ): NetworkResult<LoginResponse> {
        val body = PostRegisterRequest(nickname, profile_path)
        return handleApi { mainAPIService.postRegister(idToken = idToken, provider = provider, body = body).data }
    }

    override suspend fun postRefreshToken(refresh_token: String): NetworkResult<LoginResponse> {
        val body = PostRefreshTokenRequest(refresh_token = refresh_token)
        return handleApi { mainAPIService.postRefreshToken(body = body).data }
    }

    override suspend fun postLogin(
        idToken: String,
        provider: String
    ): NetworkResult<LoginResponse> {
        return handleApi { mainAPIService.postLogin(idToken = idToken, provider = provider).data }
    }

    override suspend fun postLogout(): NetworkResult<Unit> {
        return handleApi { mainAPIService.postLogout() }
    }

    override suspend fun getTokenValidation(
        idToken: String,
        provider: String
    ): NetworkResult<isRegistedResponse> {
        return handleApi { mainAPIService.getTokenValidation(idToken = idToken, provider = provider).data }
    }

    override suspend fun deleteUer(oauth_access_token: String): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteUer(oauth_access_token = oauth_access_token) }
    }

    override suspend fun getUserProfile(): NetworkResult<UserProfile> {
        return handleApi { mainAPIService.getUserProfile().data }
    }

    override suspend fun putUserProfile(
        nickname: String,
        profile_path: String
    ): NetworkResult<UserProfile> {
        val body = PutUserProfileRequest(nickname = nickname, profile_path = profile_path)
        return handleApi { mainAPIService.putUserProfile(body = body).data }
    }

    override suspend fun putUserNickname(nickname: String): NetworkResult<Unit> {
        val body = PutUserNicknameRequest(nickname = nickname)
        return handleApi { mainAPIService.putUserNickname(body = body) }
    }

    override suspend fun getRelations(): NetworkResult<FriendList> {
        return handleApi { mainAPIService.getRelations().data }
    }

    override suspend fun postRelations(user_id: Int): NetworkResult<Unit> {
        val body = PostRelationsRequest(user_id = user_id)
        return handleApi { mainAPIService.postRelations(body = body) }
    }

    override suspend fun getUsersNickname(nickname: String): NetworkResult<UserList> {
        return handleApi { mainAPIService.getUsersNickname(nickname = nickname).data }
    }

    override suspend fun getGroup(id: Int): NetworkResult<Group> {
        return handleApi { mainAPIService.getGroup(id = id).data }
    }

    override suspend fun getOpenGroups(
        searchString: String,
        category: Int,
        page: Int,
        size: Int
    ): NetworkResult<GroupList> {
        return handleApi { mainAPIService.getOpenGroups(searchString, category, page, size).data.toDomain() }
    }

    override suspend fun getFilterGroups(
        type: String,
        page: Int,
        size: Int
    ): NetworkResult<GroupList> {
        return handleApi { mainAPIService.getFilterGroups(type = type, page = page, size = size).data.toDomain() }
    }

    override suspend fun getFamousGroups(): NetworkResult<FamousGroupList> {
        return handleApi { mainAPIService.getFamousGroups().data }
    }

    override suspend fun putGroup(
        id: Int,
        title: String,
        description: String,
        public_access: Boolean,
        thumbnail_path: String,
        background_image_path: String,
        category_id: Int
    ): NetworkResult<Group> {
        val body = PutGroupRequest(
            title = title,
            description = description,
            public_access = public_access,
            thumbnail_path = thumbnail_path,
            background_image_path = background_image_path,
            category_id = category_id
        )
        return handleApi { mainAPIService.putGroup(id = id, body = body).data }
    }

    override suspend fun deleteGroup(id: Int): NetworkResult<Group> {
        return handleApi { mainAPIService.deleteGroup(id = id).data }
    }

    override suspend fun removeMember(id: Int, userId: Int): NetworkResult<Group> {
        return handleApi { mainAPIService.removeMember(id = id, userId = userId).data }
    }

    override suspend fun postStorages(notification_id: Int): NetworkResult<Unit> {
        return handleApi { mainAPIService.postStorages(notification_id = notification_id).data }
    }

    override suspend fun getStroages(
        groupId: Int,
        periodOfMonth: Int,
        page: Int,
        size: Int,
        sort: String
    ): NetworkResult<NotificationContent> {
        return handleApi { mainAPIService.getStroages(groupId, periodOfMonth, page, size, sort).data.pagingNotifications.toDomain() }
    }

    override suspend fun deleteStroages(storage_ids: List<Int>): NetworkResult<Unit> {
        val body = DeleteStorageRequest(storage_ids = storage_ids)
        return handleApi { mainAPIService.deleteStroages(body = body).data }
    }

    override suspend fun postReactions(
        notification_id: Int,
        reaction_id: Int
    ): NetworkResult<Unit> {
        val body = PostReactionRequest(notification_id = notification_id, reaction_id = reaction_id)
        return handleApi { mainAPIService.postReactions(body = body).data }
    }

    override suspend fun deleteReaction(notification_reaction_id: Int): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteReaction(notification_reaction_id = notification_reaction_id).data }
    }

    override suspend fun patchReaction(
        notification_reaction_id: Int,
        notification_id: Int,
        reaction_id: Int
    ): NetworkResult<Unit> {
        val body = PostReactionRequest(notification_id = notification_id, reaction_id = notification_reaction_id)
        return handleApi { mainAPIService.patchReaction(
            notification_reaction_id = notification_reaction_id,
            body = body).data
        }
    }

    override suspend fun postOptionReaction(): NetworkResult<Unit> {
        return handleApi { mainAPIService.postOptionReaction().data }
    }

    override suspend fun deleteOptionReaction(): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteOptionReaction().data }
    }

    override suspend fun postOptionNight(): NetworkResult<Unit> {
        return handleApi { mainAPIService.postOptionNight().data }
    }

    override suspend fun deleteOptionNight(): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteOptionNight().data }
    }

    override suspend fun postOptionNew(): NetworkResult<Unit> {
        return handleApi { mainAPIService.postOptionNew().data }
    }

    override suspend fun deleteOptionNew(): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteOptionNew().data }
    }

    override suspend fun getNotifications(): NetworkResult<NotificationList> {
        return handleApi { mainAPIService.getNotifications().data }
    }

    override suspend fun postNotifications(
        group_id: Int,
        title: String,
        content: String,
        image_url: String
    ): NetworkResult<Unit> {
        val body = PostNotificationRequest(
            group_id = group_id,
            title = title,
            content = content,
            image_url = image_url
        )
        return handleApi { mainAPIService.postNotifications(body = body).data }
    }

    override suspend fun postNotificationToken(
        device_id: String,
        token: String
    ): NetworkResult<Unit> {
        val body = PostNotificationTokenRequest(device_id = device_id, token = token)
        return handleApi { mainAPIService.postNotificationToken(body = body).data }
    }

    override suspend fun postNotificationReservation(
        group_id: Int,
        title: String,
        content: String,
        image_url: String,
        send_at: String
    ): NetworkResult<Unit> {
        val body = PostNotificationReservationRequest(
            group_id = group_id,
            title = title,
            content = content,
            image_url = image_url,
            send_at = send_at
        )
        return handleApi { mainAPIService.postNotificationReservation(body = body).data }
    }

    override suspend fun patchNotificationReservation(
        reservation_id: Int,
        send_at: String
    ): NetworkResult<Unit> {
        val body = PatchNotificationReservationRequest(reservation_id = reservation_id, send_at = send_at)
        return handleApi { mainAPIService.patchNotificationReservation(body = body).data }
    }

    override suspend fun postNotificationExperience(
        token: String,
        content: String
    ): NetworkResult<Unit> {
        val body = PostNotificationExperienceRequest(token = token, content = content)
        return handleApi { mainAPIService.postNotificationExperience(body = body).data }
    }

    override suspend fun getNotification(
        page: Int,
        size: Int,
        sort: String,
        group_id: Int
    ): NetworkResult<NotificationContent> {
        return handleApi { mainAPIService.getNotification(page = page, size = size, sort = sort, group_id = group_id).data.notifications }
    }

    override suspend fun deleteNotification(notification_id: Int): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteNotification(notification_id = notification_id).data }
    }

    override suspend fun deleteNotificationReservation(reservation_id: Int): NetworkResult<Unit> {
        return handleApi { mainAPIService.deleteNotificationReservation(reservation_id = reservation_id).data }
    }

    override suspend fun postFileToUrl(file : MultipartBody.Part): NetworkResult<ImageUrl> {
        return handleApi { mainAPIService.postFileToUrl(file = file).data }
    }

    override suspend fun postAddMember(id: Int, member_ids: List<Int>): NetworkResult<Group> {
        val body = PostAddMemberRequest(member_ids = member_ids)
        return handleApi { mainAPIService.postAddMember(id = id, body = body).data }
    }

    override suspend fun postEnterMembers(id: Int, code: String): NetworkResult<Group> {
        return handleApi { mainAPIService.postEnterMembers(id = id, code = code).data }
    }

    override suspend fun getGroupToken(id: Int): NetworkResult<GroupToken> {
        return handleApi { mainAPIService.getGroupToken(id = id).data }
    }

    override suspend fun deleteLeaveGroup(id: Int): NetworkResult<Group> {
        return handleApi { mainAPIService.deleteLeaveGroup(id = id).data }
    }

    override suspend fun getGroupAdmissions(id: Int): NetworkResult<Admissions> {
        return handleApi { mainAPIService.getGroupAdmissions(id = id).data }
    }

    override suspend fun postGroupAdmissions(id: Int): NetworkResult<Admission> {
        return handleApi { mainAPIService.postGroupAdmissions(id = id).data }
    }

    override suspend fun postGroupAdmissionsRefuse(
        id: Int,
        admission_id: Int
    ): NetworkResult<Admission> {
        return handleApi { mainAPIService.postGroupAdmissionsRefuse(id = id, admission_id = admission_id).data }
    }

    override suspend fun postGroupAdmissionsAllow(
        id: Int,
        admission_id: Int
    ): NetworkResult<Admission> {
        return handleApi { mainAPIService.postGroupAdmissionsAllow(id = id, admission_id = admission_id).data }
    }

    override suspend fun postGroupOpen(
        title: String,
        description: String,
        public_access: Boolean,
        thumbnail_path: String,
        background_image_path: String,
        category_id: Int,
        member_ids: List<Int>
    ): NetworkResult<Group> {
        val body = PostGroupOpenRequest(
            title = title,
            description = description,
            public_access = public_access,
            thumbnail_path = thumbnail_path,
            background_image_path = background_image_path,
            category_id = category_id,
            member_ids = member_ids
        )
        return handleApi { mainAPIService.postGroupOpen(body = body).data }
    }

    override suspend fun postGroupFriend(member_ids: List<Int>): NetworkResult<Group> {
        val body = PostGroupFriendRequest(member_ids = member_ids)
        return handleApi { mainAPIService.postGroupFriend(body = body).data }
    }

    override suspend fun getRecommendMessage(): NetworkResult<RecommendMessageList> {
        return handleApi { mainAPIService.getRecommendMessage().data }
    }

    override suspend fun getGroupCategories(): NetworkResult<CategoryList> {
        return handleApi { mainAPIService.getGroupCategories().data }
    }

    override suspend fun getGroupCategoriesFamous(): NetworkResult<CategoryList> {
        return handleApi { mainAPIService.getGroupCategoriesFamous().data }
    }

    override suspend fun getAppVersion(): NetworkResult<AppVersion> {
        return handleApi { mainAPIService.getAppVersion().data }
    }

    override suspend fun getThumbnails(): NetworkResult<ThumbnailList> {
        return handleApi { mainAPIService.getThumbnails().data }
    }

    override suspend fun getReactions(): NetworkResult<ReactionList> {
        return handleApi { mainAPIService.getReactions().data }
    }

    override suspend fun getProfiles(): NetworkResult<ProfileList> {
        return handleApi { mainAPIService.getProfiles().data }
    }

    override suspend fun getProfilesRandom(): NetworkResult<Profile> {
        return handleApi { mainAPIService.getProfilesRandom().data }
    }

    override suspend fun getBackgrounds(): NetworkResult<BackgroundList> {
        return handleApi { mainAPIService.getBackgrounds().data }
    }
}