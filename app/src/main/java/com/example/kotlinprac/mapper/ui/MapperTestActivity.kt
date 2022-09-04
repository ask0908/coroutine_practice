package com.example.kotlinprac.mapper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityMapperTestBinding
import com.example.kotlinprac.mapper.ui.adapters.CharactersAdapter
import com.example.kotlinprac.mapper.ui.viewmodel.FetchCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

/* https://medium.com/@wambuinjumbi/mapping-data-objects-between-layers-e16745f67cb2 */
@AndroidEntryPoint
class MapperTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapperTestBinding
    private lateinit var charactersAdapter: CharactersAdapter
    private val charactersViewModel: FetchCharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mapper_test)
        binding.run {
            lifecycleOwner = this@MapperTestActivity

            charactersAdapter = CharactersAdapter()
            rvCharacters.apply {
                layoutManager = LinearLayoutManager(this@MapperTestActivity)
                adapter = charactersAdapter
            }

            // 서버에서 오는 데이터를 관찰하다가 변동사항이 생기면 어댑터로 바뀐 데이터를 보내 리사이클러뷰 갱신
            charactersViewModel.fetchCharacters.observe(this@MapperTestActivity) {
                charactersAdapter.saveData(it.data)
            }

            charactersViewModel.errorResponse.observe(this@MapperTestActivity) { errorMessage ->
                Toast.makeText(this@MapperTestActivity, errorMessage.toString(), Toast.LENGTH_SHORT).show()
            }

            charactersViewModel.getCharacters()
        }
    }
}