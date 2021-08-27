package hello.world.angelkitchen.view.bottom_menu.direction

import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentFindDirectionBinding
import hello.world.angelkitchen.util.extension.setNaverMapRender

@AndroidEntryPoint
class DirectionFragment : BindingFragment<FragmentFindDirectionBinding>(R.layout.fragment_find_direction), OnMapReadyCallback {
    private val viewModel: DirectionFragmentViewModel by activityViewModels()
    private lateinit var naverMap: NaverMap

    override fun initView() {

        setNaverMapRender(R.id.container_direction_map, activity?.supportFragmentManager!!, this)

        val bundle = arguments
        if (bundle != null) {
            binding.etArrive.setText(bundle.getString("share_address"))
        }

        viewModel.curLocation.observe(this, {
            val location = it.region.area1.name + it.region.area2.name + it.region.area3.name
            binding.etStart.setText(location)
        })
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        val uiSetting = naverMap.uiSettings

        val locationSource = FusedLocationSource(this, 1000)
        naverMap.locationSource = locationSource

        // 사용자 위치 오버레이 띄움
        val locationOverlay = naverMap.locationOverlay
        locationOverlay.isVisible = true

        // 현재 위치로 이동
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        val lm = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
        val tempLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        locationOverlay.position = LatLng(tempLocation?.latitude!!, tempLocation.longitude)

        val location = locationOverlay.position
        val locationLat = location.latitude.toString()
        val locationLng = location.longitude.toString()
        val locationLonLat = "$locationLng,$locationLat"

        viewModel.getGeoApi("uzlzuhd2pa", "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc", locationLonLat)
    }
}