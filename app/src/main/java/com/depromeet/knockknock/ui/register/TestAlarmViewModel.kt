package com.depromeet.knockknock.ui.register

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.*
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.setprofile.SetProfileNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestAlarmViewModel(
    application: Application,
    private val notificationManager: NotificationManager?
) : BaseViewModel(), RegisterActionHandler {

    var inputContent = MutableStateFlow<String>("")
    var editTextMessageCountEvent = MutableStateFlow<Int>(0)
    var notificationId = 45
    var pushAlarmAgreed = MutableStateFlow<Boolean>(false)
    var CHANNEL_ID = "com.example.depromeet.knockknock.ui.register.channel1"

    private val _navigationHandler: MutableSharedFlow<RegisterNavigationAction> =
        MutableSharedFlow<RegisterNavigationAction>()
    val navigationHandler: SharedFlow<RegisterNavigationAction> = _navigationHandler.asSharedFlow()

    init {
        viewModelScope.launch {
            inputContent.debounce(0).collectLatest {
                onEditTextCount(it.length)
            }
        }
    }

    private fun onEditTextCount(count: Int) {
        viewModelScope.launch {
            editTextMessageCountEvent.value = count
        }
    }


    fun displayNotification(packageContext: Context) {
        var builder = NotificationCompat.Builder(packageContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("똑똑 미리 체험하기")
            .setContentText(inputContent.value)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(inputContent.value)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager?.notify(notificationId, builder)
    }


    fun createNotificationChannel(id: String, name: String, channelDescription: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //중요도
            val importance = NotificationManager.IMPORTANCE_HIGH
            //채널 생성
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)
        } else {
        }
    }

    fun onDeleteEditTextMessageClicked() {
        inputContent.value = ""
    }

    override fun onSendTestPushAlarmClicked() {
        baseViewModelScope.launch {
            if (!pushAlarmAgreed.value){
                _navigationHandler.emit(RegisterNavigationAction.NavigateToPushSetting)
            }
            else
                _navigationHandler.emit(RegisterNavigationAction.NavigateToNotificationAlarm)
        }
    }
}


@Suppress("UNCHECKED_CAST")
class TestAlarmViewModelFactory(
    private val application: Application,
    private val notificationManager: NotificationManager?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestAlarmViewModel::class.java)) {

            return TestAlarmViewModel(application, notificationManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}