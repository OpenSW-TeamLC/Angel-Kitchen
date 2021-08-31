package hello.world.angelkitchen.view.bottom_menu.direction

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import android.os.Looper
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.PathOverlay
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
    private lateinit var currentLocation: Location
    private lateinit var currentLocationLatLng: String
    private lateinit var goalGeocode: String

    override fun initView() {

        setNaverMapRender(R.id.container_direction_map, activity?.supportFragmentManager!!, this)

        val bundle = arguments
        if (bundle != null) {
            binding.etArrive.setText(bundle.getString("share_address"))
        }

        viewModel.curLocation.observe(this, {
            val location =
                "${it.region?.area1?.name} ${it.region?.area2?.name} ${it.region?.area3?.name} ${it.region?.area4?.name} ${it.land?.number1}-${it.land?.number2}"
            binding.etStart.setText(location)
        })

        viewModel.goalLocation.observe(this, {
            goalGeocode = "${viewModel.goalLocation.value?.x},${viewModel.goalLocation.value?.y}"
            viewModel.getResultPath(
                "uzlzuhd2pa",
                "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                currentLocationLatLng,
                goalGeocode
            )
        })

        viewModel.getResultPath.observe(this, {
            val path = PathOverlay()
            val pathContainer : MutableList<LatLng> = mutableListOf(LatLng(0.1,0.1))
            if(it != null) {
                for(pathCode in it) {
                    for(pathCodeXY in pathCode.path) {
                        pathContainer.add(LatLng(pathCodeXY[1], pathCodeXY[0]))
                    }
                }
            }
            path.coords = pathContainer.drop(1)
            path.color = Color.RED
            path.map = naverMap

            val cameraUpdate = CameraUpdate.scrollTo(path.coords[0]!!)
                .animate(CameraAnimation.Easing, 3000)
            naverMap.moveCamera(cameraUpdate)
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val locationRequest = LocationRequest.create().apply {
//            interval = 100
//            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 3000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(location: LocationResult) {
                super.onLocationResult(location)
                currentLocation = location.lastLocation
                val lat = currentLocation.latitude
                val lng = currentLocation.longitude
                currentLocationLatLng = "$lng,$lat"
                viewModel.getReverseGeoApi(
                    "uzlzuhd2pa",
                    "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                    currentLocationLatLng
                )
            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )

        binding.btnSearch.setOnClickListener {
            viewModel.getGeoApi(
                "uzlzuhd2pa",
                "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc",
                binding.etArrive.text.toString().replace(" ", "")
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}