package hello.world.angelkitchen.view.bottom_menu

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.databinding.ActivityBottomMenuBinding
import hello.world.angelkitchen.util.extension.replace
import hello.world.angelkitchen.util.extension.replaceToBackStack
import hello.world.angelkitchen.view.bottom_menu.bookmark.BookmarkFragment
import hello.world.angelkitchen.view.bottom_menu.direction.DirectionFragment
import hello.world.angelkitchen.view.bottom_menu.search.SearchFragment
import hello.world.angelkitchen.view.bottom_menu.search.SearchViewModel
import hello.world.angelkitchen.view.bottom_menu.search.search_result.SearchResultFragment
import hello.world.angelkitchen.view.bottom_menu.setting.SettingFragment

@AndroidEntryPoint
class BottomMenuActivity : BindingActivity<ActivityBottomMenuBinding>(R.layout.activity_bottom_menu) {
    private val searchFragment: SearchFragment by lazy { SearchFragment() }
    private val directionFragment: DirectionFragment by lazy { DirectionFragment() }
    private val bookmarkFragment: BookmarkFragment by lazy { BookmarkFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }
    private val searchResultFragment: SearchResultFragment by lazy { SearchResultFragment() }
    private val viewModel: SearchViewModel by viewModels()

    override fun initView() {
        val isSelectedBottomMenu = intent.extras?.getString("bottom_nav")!!
        Log.d("BottomMenuActivity", isSelectedBottomMenu)
        replaceFragment(isSelectedBottomMenu)

        viewModel.sharePlace.observe(this, {
            replaceToBackStack(R.id.container_main, searchResultFragment)
        })
    }

    override fun startView() {

    }

    override fun stopView() {

    }

    private fun replaceFragment(bnvMenu: String) {
        when(bnvMenu) {
            "bnv_search" -> {
                replaceSearchFragment()
            }
            "bnv_direction" -> {
                replaceDirectionFragment()
            }
            "bnv_bookmark" -> {
                replaceBookmarkFragment()
            }
            "bnv_setting" -> {
                replaceSettingFragment()
            }
        }
    }

    private fun replaceSearchFragment() {
        replace(R.id.container_main, searchFragment)
    }

    private fun replaceDirectionFragment() {
        replace(R.id.container_main, directionFragment)
    }

    private fun replaceBookmarkFragment() {
        replace(R.id.container_main, bookmarkFragment)
    }

    private fun replaceSettingFragment() {
        replace(R.id.container_main, settingFragment)
    }
}