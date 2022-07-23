package com.example.kotlinprac.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityDataStoreBinding
import com.example.kotlinprac.databinding.ActivityDataStoreTestBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataStoreTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataStoreTestBinding
    private lateinit var userManager: UserManager
    private var age = -1
    private var frontName = ""
    private var lastName = ""
    private var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_store_test)

        // UserManager 클래스의 참조 얻음
        // 여기서 사용한 dataStore는 Context의 톱레벨 함수다
        userManager = UserManager(dataStore)

        binding.run {
            buttonSave()
            observeData()
        }
    }

    private fun ActivityDataStoreTestBinding.buttonSave() {
        btnSave.setOnClickListener {
            frontName = etFname.text.toString()
            lastName = etLname.text.toString()
            age = etAge.text.toString().toInt()
            val isMale = switchGender.isChecked

            CoroutineScope(IO).launch {
                userManager.storeUser(age, frontName, lastName, isMale)
            }
        }
    }

    private fun observeData() {
        // 나이 업데이트
        userManager.userAgeFlow.asLiveData().observe(this) {
            if (it != null) {
                age = it
                binding.tvAge.text = it.toString()
            }
        }

        // 이름 업데이트
        userManager.userFirstNameFlow.asLiveData().observe(this) {
            if (it != null) {
                frontName = it
                binding.tvFname.text = it
            }
        }

        // 마지막 이름 업데이트
        userManager.userLastNameFlow.asLiveData().observe(this) {
            if (it != null) {
                lastName = it
                binding.tvLname.text = it
            }
        }

        // 성별 업데이트
        userManager.userGenderFlow.asLiveData().observe(this) {
            if (it != null) {
                gender = if (it) "남성" else "여성"
                binding.tvGender.text = gender
            }
        }
    }

}