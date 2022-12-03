package com.depromeet.knockknock.util.permission

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * It should be one-per-fragment manager.
 * Immediately subscribes to activity callbacks
 */
class PermissionManagerImpl(
    private var fragment: Fragment?
) : PermissionManager, LifecycleObserver {

    private val launchers = mutableMapOf<Permission, ActivityResultLauncher<String>>()
    private val observers = mutableMapOf<Permission, PermissionInfo>()

    /**
     * Stores whether Rationale has been shown for specific [Permission] of permission.
     * Should be reset after every access granting
     */
    private val shownRationale = mutableMapOf<Permission, Boolean>()

    init {
        // it should be non-null here
        bind(fragment)
    }

    private fun bind(fragment: Fragment?) {
        this.fragment = fragment
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun unbind() {
        val previousValues = launchers.toMap()
        previousValues.values.forEach { it.unregister() }
        launchers.clear()
        observers.clear()
        fragment = null
    }

    private fun processPermissionReply(type: Permission, isGranted: Boolean) {
        observers[type]?.let { observer ->
            if (isGranted) {
                observer.granted?.invoke()
            } else {
                // by indicating whether rationale should be shown we can assume that user
                // clicked "Deny&don't ask again"
                fragment?.let {
                    val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                        it.requireActivity(),
                        type.name
                    )
                    if (shouldShowRationale)
                        observer.denied?.invoke()
                    else
                        observer.permanentlyDenied?.invoke()
                }
            }
            // in any case it should be reset because user made a decision
            shownRationale[type] = false
        }
    }

    override fun hasPermission(permission: Permission): Boolean {
        return fragment?.let {
            ContextCompat.checkSelfPermission(
                it.requireContext(),
                permission.name
            ) == PackageManager.PERMISSION_GRANTED
        } ?: false
    }

    override fun requestPermission(info: PermissionInfo) {
        observers[info.type] = info

        if (hasPermission(info.type)) {
            info.granted?.invoke()
        } else {
            val activity = fragment?.activity ?: return
            val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                info.type.name
            ) && !(shownRationale[info.type] ?: false)

            if (shouldShowRationale) {
                shownRationale[info.type] = true
                info.rationale?.invoke()
            } else {
                launchers[info.type]?.launch(info.type.name)
            }
        }
    }

    override fun forPermission(type: Permission): PermissionInfo {
        return PermissionInfo(type, this)
    }

    override fun subscribe(info: PermissionInfo) {
        fragment?.let {
            val launcher =
                it.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    processPermissionReply(info.type, isGranted)
                }
            launchers[info.type] = launcher
            observers[info.type] = info
        }
    }
}