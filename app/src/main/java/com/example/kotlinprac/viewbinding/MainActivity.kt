package com.example.kotlinprac.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinprac.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CustomAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        binding.buttonRequest.setOnClickListener {
            val githubService = retrofit.create(GithubService::class.java)
            githubService.users().enqueue(object: Callback<Repository> {
                override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                    adapter.userList = response.body() as Repository
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Repository>, t: Throwable) {
                    //
                }
            })
        }
    }
}

// 인터페이스 따로 만들기 귀찮아서 내부 인터페이스로 만듬
interface GithubService {
    // 깃허브 레포 중 Kotlin 관련 레포들을 가져옴
    @GET("users/Kotlin/repos")
    fun users(): Call<Repository>
}