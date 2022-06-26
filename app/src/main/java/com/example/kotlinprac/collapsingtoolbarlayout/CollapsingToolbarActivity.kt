package com.example.kotlinprac.collapsingtoolbarlayout

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityCollapsingToolbarBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

/* OnOffsetChangedListener : AppBarLayout의 수직 오프셋이 바뀔 때 호출되는 콜백에 대한 인터페이스 */
class CollapsingToolbarActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    private lateinit var binding: ActivityCollapsingToolbarBinding

    // 툴바 안의 글자를 보여주기 위해 스크롤을 얼마나 내려야 할지에 대한 기준값
    private val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f
    // 툴바 밑 레이아웃 안의 글자들이 언제 안 보여야 할지에 대한 기준값
    private val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f
    // 몇 초에 걸쳐 애니메이션이 작동하게 할 것인가
    private val ALPHA_ANIMATIONS_DURATION = 200L

    // 툴바 안의 제목이 보이는지 여부를 확인할 때 사용할 변수
    private var mIsTheTitleVisible = false
    // NestedScrollView 위의 텍스트뷰들이 보이는지 여부를 확인할 때 사용할 변수
    private var mIsTheTitleContainerVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collapsing_toolbar)
        binding.run {
            lifecycleOwner = this@CollapsingToolbarActivity
            mainAppbar.addOnOffsetChangedListener(this@CollapsingToolbarActivity)
            mainToolbar.inflateMenu(R.menu.menu_main)
            setSupportActionBar(mainToolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            startAlphaAnimation(mainTextviewTitle, 0, INVISIBLE)
        }
    }

    /* 화면 우상단의 FAVORITE, 세로 점 3개 메뉴가 표시되게 처리 */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "뒤로가기 버튼 클릭", Toast.LENGTH_SHORT).show()
            }
            R.id.action_settings -> {
                Toast.makeText(this, "클릭", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * AppBarLayout.OnOffsetChangedListener 시 재정의해야 하는 메서드. 스크롤이 변할 때 호출된다
     */
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, offset: Int) {
        // 모든 자식 뷰들의 스크롤 범위를 리턴받아서 사용자의 핸드폰에서 최대 어디까지 스크롤할 수 있는지 구한다
        val maxScroll = appBarLayout!!.totalScrollRange
        // 어디까지 스크롤해야 툴바 안의 글자들이 보일지에 대한 기준값을 구한다
        val percentage = abs(offset).toFloat() / maxScroll.toFloat()

        // 기준값에 따라 툴바 또는 툴바 밑 레이아웃의 글자들이 안 보이게 애니메이션 효과로 Fade in, Fade out 처리한다
        handleAlphaOnTitle(percentage)
        handleToolbarTitleVisibility(percentage)
    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        // 사용자 스크롤이 NestedScrollView NestedScrollView까지 스크롤하게 됐을 때
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(binding.mainTextviewTitle, ALPHA_ANIMATIONS_DURATION, VISIBLE)
                startAlphaAnimation(binding.mainTextviewSubTitle, ALPHA_ANIMATIONS_DURATION, VISIBLE)
                binding.mainTextviewTitle.text = "Title"
                binding.mainTextviewSubTitle.text = "subTitle"
                mIsTheTitleVisible = true
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(binding.mainTextviewTitle, ALPHA_ANIMATIONS_DURATION, INVISIBLE)
                startAlphaAnimation(binding.mainTextviewSubTitle, ALPHA_ANIMATIONS_DURATION, INVISIBLE)
                mIsTheTitleVisible = false
            }
        }
    }

    private fun handleAlphaOnTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(binding.mainLinearlayoutTitle, ALPHA_ANIMATIONS_DURATION, INVISIBLE)
                mIsTheTitleContainerVisible = false
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(binding.mainLinearlayoutTitle, ALPHA_ANIMATIONS_DURATION, VISIBLE)
                mIsTheTitleContainerVisible = true
            }
        }
    }

    /**
     * 툴바 또는 CollapsingToolbarLayout 안의 뷰들에 투명도를 입혀 보이게 할지 여부를 정하는 메서드
     * 스크롤할 때 CollapsingToolbarLayout 안의 뷰들은 사라지고 툴바의 텍스트들만 보여야 하고, 원래대로 돌아올 땐 그 반대로 보여야 한다
     * @param view - 보여지고 사라지는 처리를 적용할 대상 뷰
     * @param duration - 몇 초에 걸쳐서 보여지고 사라지는 처리를 할 것인가에 대한 매개변수
     * @param visibility - VISIBLE, INVISIBLE, GONE 중 하나
     */
    private fun startAlphaAnimation(view: View, duration: Long, visibility: Int) {
        // fade in, fade out 처리
        val alphaAnimation =
            if (visibility == VISIBLE) AlphaAnimation(0f, 1f)
            else AlphaAnimation(1f, 0f)
        alphaAnimation.apply {
            this.duration = duration
            this.fillAfter = true
            view.startAnimation(this)
        }
    }

}