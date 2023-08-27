package com.example.kotlinprac.photopicker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityPhotoPickerTestBinding
import timber.log.Timber

class PhotoPickerTestActivity :
    BaseActivity<ActivityPhotoPickerTestBinding>(R.layout.activity_photo_picker_test) {

    private lateinit var pickSingleMediaLauncher: ActivityResultLauncher<Intent>
    private lateinit var pickMultipleMediaLauncher: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickSingleMediaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    it.data?.data?.let { uri ->
                        Timber.e("## uri : $uri")
                    }
                }
            }

        pickMultipleMediaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val uris = it.data?.clipData ?: return@registerForActivityResult
                    var uriPaths = ""
                    for (index in 0 until uris.itemCount) {
                        uriPaths += uris.getItemAt(index).uri.path
                        Timber.e("## uriPaths : $uriPaths")
                    }
                }
            }

        bind {
            btnSelectSingleImage.setOnClickListener {
                pickSingleMediaLauncher.launch(
                    Intent(MediaStore.ACTION_PICK_IMAGES).apply {
                        type = "image/*"
                    })
            }

            btnSelectMultipleImage.setOnClickListener {
                pickSingleMediaLauncher.launch(
                    Intent(MediaStore.ACTION_PICK_IMAGES).apply {
                        type = "image/*"
                        putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, 3) // 최대 3장까지
                    })
            }

            btnSelectVideo.setOnClickListener {
                pickSingleMediaLauncher.launch(
                    Intent(MediaStore.ACTION_PICK_IMAGES)
                        .apply {
                            type = "video/*"
                        }
                )
            }
        }
    }
}