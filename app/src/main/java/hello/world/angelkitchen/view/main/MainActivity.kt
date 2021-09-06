package hello.world.angelkitchen.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.UiThread
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenPostData
import hello.world.angelkitchen.databinding.ActivityMainBinding
import hello.world.angelkitchen.util.extension.setNaverMapRender
import hello.world.angelkitchen.view.bottom_menu.BottomMenuActivity
import timber.log.Timber
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main),
    OnMapReadyCallback {

    private val viewModel: MainActivityViewModel by viewModels()
    private val markerList = mutableListOf<Marker>()
    private lateinit var currentCameraPosition: CameraPosition
    private lateinit var naverMap: NaverMap

    override fun initView() {
//        Call Dialog
//        AddActionExitDialogFragment().show(supportFragmentManager, "Test")

        initBottomNavigation()
        setMarkerInMap()
    }

    private fun setMarkerInMap() {
        val infoWindow = InfoWindow()
        viewModel.angelThreeDataList.observe(this, {
            naverMap.locationTrackingMode = LocationTrackingMode.NoFollow
            for (markerIndex in it.items.indices) {
                val marker = Marker()
                marker.position = LatLng(
                    it.items[markerIndex].latitude,
                    it.items[markerIndex].longitude
                )
                Timber.d(it.items[markerIndex].fcltyNm)
                marker.tag = it.items[markerIndex].fcltyNm
                marker.setOnClickListener {
                    infoWindow.open(marker)
                    true
                }
                markerList.add(marker)
            }
            Timber.d("---------------------------------")
            for (markerPosition in markerList) {
                if (withinSightMarker(
                        LatLng(
                            currentCameraPosition.target.latitude,
                            currentCameraPosition.target.longitude
                        ), markerPosition.position
                    )
                ) {
                    markerPosition.map = naverMap
                } else {
                    markerPosition.map = null
                }
            }
        })

        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(p0: InfoWindow): CharSequence {
                return p0.marker?.tag as CharSequence
            }
        }
    }

    override fun startView() {
        setNaverMapRendering()
    }

    override fun stopView() {

    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        binding.btnLocation.map = naverMap

        val locationSource = FusedLocationSource(this, 1000)
        naverMap.locationSource = locationSource

        val locationOverlay = naverMap.locationOverlay
        locationOverlay.isVisible = true

        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        naverMap.addOnCameraChangeListener { _, _ ->
            currentCameraPosition = naverMap.cameraPosition
        }

        naverMap.addOnCameraIdleListener {
//            viewModel.clearDataList()
            viewModel.getThreeData(
                AngelScanKitchenPostData(
                    currentCameraPosition.target.latitude,
                    currentCameraPosition.target.longitude,
                    3
                )
            )
        }
    }

    private val REFERANCE_LAT_X3 = 3 / 109.958489129649955//마커 가시 반경 설정(현재 3km)
    private val REFERANCE_LNG_X3 = 3 / 88.74//마커 가시 반경 설정(현재 3km)
    private fun withinSightMarker(currentPosition: LatLng, markerPosition: LatLng): Boolean {
        val withinSightMarkerLat =
            abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3
        val withinSightMarkerLng =
            abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3
        return withinSightMarkerLat && withinSightMarkerLng
    }

    private fun initBottomNavigation() {
        val intent = Intent(this@MainActivity, BottomMenuActivity::class.java)
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_menu_search -> {
                    intent.putExtra("bottom_nav", "bnv_search")
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_menu_direction -> {
                    intent.putExtra("bottom_nav", "bnv_direction")
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_menu_bookmark -> {
                    intent.putExtra("bottom_nav", "bnv_bookmark")
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bottom_menu_setting -> {
                    intent.putExtra("bottom_nav", "bnv_setting")
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setNaverMapRendering() {
        setNaverMapRender(R.id.container_map, supportFragmentManager, this)
    }
}