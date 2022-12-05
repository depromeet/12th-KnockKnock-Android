package com.depromeet.knockknock.ui.register

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.depromeet.knockknock.R
import kotlinx.coroutines.flow.MutableStateFlow

class TestAlarmViewModel(application: Application, private val notificationManager: NotificationManager?) : AndroidViewModel(application) {

    var inputContent = MutableStateFlow<String>("")
    var notificationId = 45
    var CHANNEL_ID = "com.example.depromeet.knockknock.ui.register.channel1"


    fun displayNotification(packageContext: Context){
        var builder = NotificationCompat.Builder(packageContext,CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("똑똑 미리 체험하기")
            .setContentText(inputContent.value)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(inputContent.value))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager?.notify(notificationId,builder)
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