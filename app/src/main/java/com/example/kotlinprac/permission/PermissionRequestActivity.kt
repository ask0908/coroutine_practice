package com.example.kotlinprac.permission

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityPermissionRequestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("NewApi")
class PermissionRequestActivity :
    BaseActivity<ActivityPermissionRequestBinding>(R.layout.activity_permission_request) {

    private val permissionRequestViewModel: PermissionRequestViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissions = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkCurrentPermissionState()
        setupPermissionStateObserver()
        setupPermissionRequestButton()
    }

    private fun checkCurrentPermissionState() {
        val isPermissionGranted = permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
        permissionRequestViewModel.updatePermissionState(isPermissionGranted)
    }

    private fun setupPermissionStateObserver() = lifecycleScope.launch {
        permissionRequestViewModel.permissionState.collect { isAllowed ->
            if (isAllowed) {
                updateUIForGrantedPermission()
            } else {
                updateUIForDeniedPermission()
            }
        }
    }

    private fun updateUIForGrantedPermission() {
        binding.ivPermissionState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.allowed))
        binding.tvPermissionState.text = "권한 허용됨!!"
    }

    private fun updateUIForDeniedPermission() {
        binding.ivPermissionState.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.not_allowed))
        binding.tvPermissionState.text = "권한 허용 안 됨!!"
    }

    private fun setupPermissionRequestButton() = bind {
        btnRequestPermission.setOnClickListener {
            if (isPermissionGranted(this@PermissionRequestActivity)) {
                permissionRequestViewModel.updatePermissionState(true)
            } else {
                requestPermissions(this@PermissionRequestActivity, 100)
            }
        }
    }

    private fun isPermissionGranted(activity: Activity) = permissions.all {
        ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions(activity: Activity, requestCode: Int) =
        ActivityCompat.requestPermissions(activity, permissions, requestCode)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> {
                binding.tvPermissionState.text = "권한 허용됨!!"
                permissionRequestViewModel.updatePermissionState(true)
            }
        }
    }

}