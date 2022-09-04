package com.example.kotlinprac.mapper.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinprac.databinding.CharactersItemsBinding
import com.example.kotlinprac.mapper.CharacterDetails

/* https://medium.com/@wambuinjumbi/using-diffutil-in-a-recyclerview-da38d64c83f2
* diffUtil을 써서 리소스를 많이 잡아먹는 notifyDataSetChanged()를 대체함 */
class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharactersAdapterVh>() {
    // 어댑터 안에 lateinit var binding 프로퍼티를 만들고 초기화해서 쓰지 않아도 뷰홀더에서 사용할 수 있다?
    class CharactersAdapterVh(var binding: CharactersItemsBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<CharacterDetails>() {
        // 현재 리스트, 새 리스트의 아이템이 같은지 확인
        override fun areItemsTheSame(
            oldItem: CharacterDetails,
            newItem: CharacterDetails
        ): Boolean {
            return oldItem.id == newItem.id
        }

        // 두 리스트 아이템 중 같은 게 있는지 확인
        override fun areContentsTheSame(
            oldItem: CharacterDetails,
            newItem: CharacterDetails
        ): Boolean {
            return oldItem == newItem
        }

    }

    // AsyncListDiffer : 백그라운드 쓰레드에서 두 리스트의 차이를 계산하는 DiffUtil Helper
    // 차이 계산 후 두 리스트의 변경사항을 어댑터에 알림
    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    // 데이터를 리사이클러뷰 레이아웃으로 설정하기 위해 액티비티에서 호출될 함수
    fun saveData(dataResponse: List<CharacterDetails>) = asyncListDiffer.submitList(dataResponse)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersAdapterVh {
        val binding = CharactersItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersAdapterVh(binding)
    }

    override fun onBindViewHolder(holder: CharactersAdapterVh, position: Int) {
        val data = asyncListDiffer.currentList[position]
        holder.binding.apply {
            tvCharacterName.text = data.characterName

            Glide.with(holder.itemView.context)
                .load(data.profileImageUrl)
                .centerCrop()
                .into(profileImage)
        }


    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }
}