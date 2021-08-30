package hello.world.angelkitchen.view.bottom_menu.direction

import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
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
class DirectionFragment :
    BindingFragment<FragmentFindDirectionBinding>(R.layout.fragment_find_direction),
    OnMapReadyCallback {
    private val viewModel: DirectionFragmentViewModel by activityViewModels()
    private lateinit var naverMap: NaverMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    var i = 0

    override fun initView() {

        setNaverMapRender(R.id.container_direction_map, activity?.supportFragmentManager!!, this)

        val bundle = arguments
        if (bundle != null) {
            binding.etArrive.setText(bundle.getString("share_address"))
        }

        viewModel.curLocation.observe(this, {
            val location = "${it.region.area1.name} ${it.region.area2.name} ${it.region.area3.name} $i"
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val locationRequest = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 3000
        }

        var currentLocation: Location
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                currentLocation = p0.lastLocation
                val lat = currentLocation.latitude
                val lng = currentLocation.longitude
                val locationLonLat = "$lng,$lat"
                viewModel.getGeoApi(
                    "uzlzuhd2pa",
                    "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                    locationLonLat
                )
                Toast.makeText(activity, locationLonLat, Toast.LENGTH_SHORT).show()
                i++
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}