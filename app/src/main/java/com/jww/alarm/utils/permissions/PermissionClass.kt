package com.jww.alarm.utils.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jww.alarm.BuildConfig

class PermissionClass : AppCompatActivity() {

    companion object {
        val INTENT_PERMISSION = "permission"
        val REQUEST_CODE_PERMISSION = 9001

        val mainPermission = arrayListOf(
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.VIBRATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.CAMERA
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let {
            val permissionList = it.getStringArrayListExtra(INTENT_PERMISSION)
            permissionList?.let { list ->
                requestPermission(list)
            }
        }

    }

    fun isPermission(activity: Activity, requestPermission: ArrayList<String>) =
        isCheckPermission(activity, requestPermission)

    fun checkSettingPermission(context: Context){
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID))
        context.startActivity(intent)
    }

    private fun isCheckPermission(activity: Activity, permissionList: ArrayList<String>): Boolean {
        permissionList.forEach {
            val result = ContextCompat.checkSelfPermission(activity, it)
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty()) {
                var isAllPermission = true
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        isAllPermission = false
                        break
                    }
                }
                if (isAllPermission) {
                    resultSuccess()
                } else {
                    resultFailed()
                }
            }
        }
    }

    private fun resultSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun resultFailed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun requestPermission(permissionList: ArrayList<String>) {
        if (checkPermission(permissionList, true)) {
            resultSuccess()
        }
    }

    private fun checkPermission(
        permissionList: ArrayList<String>,
        isNeedRequest: Boolean
    ): Boolean {

        val permissionNeedList: ArrayList<String> = arrayListOf()
        permissionList.forEach {
            val result = ContextCompat.checkSelfPermission(this, it)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionNeedList.add(it)
            }
        }

        if (permissionNeedList.isNotEmpty()) {
            if (isNeedRequest) {
                ActivityCompat.requestPermissions(
                    this,
                    permissionNeedList.toTypedArray(),
                    REQUEST_CODE_PERMISSION
                )
            }
            return false
        }
        return true
    }
}