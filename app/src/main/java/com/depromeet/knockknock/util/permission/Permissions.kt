package com.depromeet.knockknock.util.permission


import android.Manifest
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * Class which holds callbacks for permission and some other information
 */
open class PermissionInfo(val type: Permission, val permissionManager: PermissionManager) {

    constructor(info: PermissionInfo) : this(info.type, info.permissionManager) {
        granted = info.granted
        denied = info.denied
        rationale = info.rationale
        permanentlyDenied = info.permanentlyDenied
    }

    var granted: PermissionAction? = null
        private set
    var denied: PermissionAction? = null
        private set
    var permanentlyDenied: PermissionAction? = null
        private set
    var rationale: PermissionAction? = null
        private set

    /**
     * Use this to pass all callbacks at once
     */
    fun withCallback(
        onGranted: PermissionAction? = null,
        onDenied: PermissionAction? = null,
        onPermanentlyDenied: PermissionAction? = null,
        onRationale: PermissionAction? = null
    ): PermissionInfo {
        granted = onGranted
        denied = onDenied
        rationale = onRationale
        permanentlyDenied = onPermanentlyDenied
        return this
    }

    fun onGranted(action: PermissionAction): PermissionInfo {
        return this.apply {
            granted = action
        }
    }

    fun onDenied(action: PermissionAction): PermissionInfo {
        return this.apply {
            denied = action
            if (permanentlyDenied == null) {
                permanentlyDenied = denied
            }
        }
    }

    fun onPermanentlyDenied(action: PermissionAction): PermissionInfo {
        return this.apply {
            permanentlyDenied = action
        }
    }

    fun onRationale(action: PermissionAction): PermissionInfo {
        return this.apply {
            rationale = action
        }
    }

    /**
     * Removes all callbacks
     */
    fun clearCallbacks() {
        granted = null
        denied = null
        rationale = null
        permanentlyDenied = null
    }

    fun subscribe(lifecycleOwner: LifecycleOwner? = null): PermissionRequester {
        val info = if (lifecycleOwner == null) {
            this
        } else {
            LifecycleAwareInfo(this, lifecycleOwner)
        }

        return PermissionRequester(info).also { info.permissionManager.subscribe(info) }
    }
}

/**
 * It automatically subscribes to the lifecycle and clears actions after onDestroy event
 */
class LifecycleAwareInfo(
    private val info: PermissionInfo,
    lifecycleOwner: LifecycleOwner
) : PermissionInfo(info), LifecycleObserver {

    var lifecycleOwner: LifecycleOwner? = null

    init {
        this.lifecycleOwner = lifecycleOwner
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun removeActions() {
        Log.d("QQQ", "removeActions")
        info.permissionManager.unbind()
        lifecycleOwner?.lifecycle?.removeObserver(this)
    }
}

typealias PermissionAction = () -> Unit

/**
 * Essentially, it is the class to postpone to request itself
 */
class PermissionRequester(private val info: PermissionInfo) {
    fun request() {
        info.permissionManager.requestPermission(info)
    }
}

/**
 * Description of the permission. Name for now
 */
open class Permission(val name: String)

/**
 * Possible types of Dangerous permission. Might be extended in future
 */
object Permissions {
    object Camera : Permission(Manifest.permission.CAMERA)
    object WriteExternalStorage : Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    object FineLocation : Permission(Manifest.permission.ACCESS_FINE_LOCATION)
    object CoarseLocation : Permission(Manifest.permission.ACCESS_COARSE_LOCATION)
//    @RequiresApi(33)
//    object PostNotification : Permission(Manifest.permission.POST_NOTIFICATIONS)
}