package hello.world.angelkitchen

import android.content.Intent
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.databinding.ActivityMainBinding
import hello.world.angelkitchen.view.bottom_menu.BottomMenuActivity
import hello.world.angelkitchen.view.bottom_menu.bookmark.BookmarkFragment
import hello.world.angelkitchen.view.bottom_menu.direction.DirectionFragment
import hello.world.angelkitchen.view.onboard.OnboardActivity
import hello.world.angelkitchen.view.bottom_menu.search.SearchFragment
import hello.world.angelkitchen.view.bottom_menu.setting.SettingFragment

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main),
    OnMapReadyCallback {

    private lateinit var naverMap: NaverMap

    override fun initView() {
//        Call Dialog
//        AddActionExitDialogFragment().show(supportFragmentManager, "Test")

        initBottomNavigation()

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.container_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.container_map, it).commit()
            }
        mapFragment.getMapAsync { naverMap = it }
    }

    override fun startView() {

    }

    override fun stopView() {

    }

    override fun onMapReady(p0: NaverMap) {

    }

    private fun initBottomNavigation() {
        val intent = Intent(this@MainActivity, BottomMenuActivity::class.java)
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_menu_search -> {
                    intent.putExtra("bottom_nav", "bnv_search")
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_menu_direction -> {
                    intent.putExtra("bottom_nav", "bnv_direction")
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_menu_bookmark -> {
                    intent.putExtra("bottom_nav", "bnv_bookmark")
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_menu_setting -> {
                    intent.putExtra("bottom_nav", "bnv_setting")
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}
