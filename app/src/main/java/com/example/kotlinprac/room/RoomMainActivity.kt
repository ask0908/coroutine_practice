package com.example.kotlinprac.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.kotlinprac.R

// https://developer.android.com/codelabs/basic-android-kotlin-training-persisting-data-room?hl=ko#3
class RoomMainActivity : AppCompatActivity(R.layout.activity_room_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // NavHostFragment에서 NavController 검색
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // NavController와 같이 사용할 작업 표시줄 설정. import 잘못 하지 않게 주의
        setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()

}