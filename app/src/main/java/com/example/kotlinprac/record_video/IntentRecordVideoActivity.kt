package com.example.kotlinprac.record_video

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityIntentRecordVideoBinding
import timber.log.Timber

class IntentRecordVideoActivity :
    BaseActivity<ActivityIntentRecordVideoBinding>(R.layout.activity_intent_record_video) {

    private val permissionsUnder13 = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissionsOver13 = arrayOf(
        Manifest.permission.READ_MEDIA_VIDEO,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            btnRecordVideoIntent.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (!allPermissionsGranted(permissionsOver13)) {
                        requestPermissions(permissionsOver13, 101)
                    } else {
                        dispatchTakeVideoIntent()
                    }
                } else {
                    if (!allPermissionsGranted(permissionsUnder13)) {
                        requestPermissions(permissionsUnder13, 101)
                    } else {
                        dispatchTakeVideoIntent()
                    }
                }
            }
        }
    }

    private fun allPermissionsGranted(permissions: Array<String>): Boolean =
        permissions.all {
            checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }

    private fun dispatchTakeVideoIntent() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val videoUri: Uri? = data?.data
            videoUri?.let {
                Timber.e("## videoUri : $videoUri")
            } ?: Timber.e("## videoUri == null")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            dispatchTakeVideoIntent()
        } else {
            Toast.makeText(this, "Permissions are required to record a video", Toast.LENGTH_SHORT).show()
        }
    }
}