package hello.world.angelkitchen.view.bottom_menu.direction

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
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
    private var zoomRatio: Double = 11.0
    private lateinit var naverMap: NaverMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentLocation: Location
    private lateinit var currentLocationLatLng: String
    private lateinit var goalGeocode: String

    override fun initView() {
        setNaverMapRender(R.id.container_direction_map, activity?.supportFragmentManager!!, this)
        getDataFromActivity()
        observeData()
    }

    private fun observeData() {
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
            var routesCount = 0
            val pathContainer: MutableList<LatLng> = mutableListOf(LatLng(0.1, 0.1))
            if (it != null) {
                for (pathCode in it) {
                    for (pathCodeXY in pathCode.path) {
                        pathContainer.add(LatLng(pathCodeXY[1], pathCodeXY[0]))
                        routesCount++
                    }
                }
            }
            path.coords = pathContainer.drop(1)
            path.color = Color.RED
            path.map = naverMap

            zoomRatio = when ((it[0].summary.distance * 0.001).toInt()) {
                in 10..29 -> 11.0
                in 30..59 -> 10.0
                in 60..89 -> 9.0
                in 90..99 -> 7.0
                in 100..999 -> 3.0
                else -> 11.0
            }

            val CenterLatlng =
                LatLng(it[0].path[routesCount / 2][1], it[0].path[routesCount / 2][0])
            naverMap.moveCamera(
                CameraUpdate.scrollAndZoomTo(CenterLatlng, zoomRatio)
                    .animate(CameraAnimation.Fly, 2000)
            )

            val marker = Marker()
            marker.position = LatLng(it[0].path[routesCount - 1][1], it[0].path[routesCount - 1][0])
            marker.map = naverMap

            binding.btnNavigation.visibility = View.VISIBLE
        })
    }

    private fun getDataFromActivity() {
        val bundle = arguments
        if (bundle != null) {
            binding.etArrive.setText(bundle.getString("share_address"))
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        val uiSetting = naverMap.uiSettings
        binding.btnLocation.map = naverMap

        val locationSource = FusedLocationSource(this, 1000)
        naverMap.locationSource = locationSource

        // 사용자 위치 오버레이 띄움
        val locationOverlay = naverMap.locationOverlay
        locationOverlay.isVisible = true

        // 현재 위치로 이동
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
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

        binding.btnNavigation.setOnClickListener {
            zoomRatio = 17.0
            naverMap.moveCamera(
                CameraUpdate.toCameraPosition(
                    CameraPosition(
                        LatLng(
                            currentLocation.latitude,
                            currentLocation.longitude
                        ), zoomRatio
                    )
                )
            )
            naverMap.moveCamera(
                CameraUpdate.zoomTo(zoomRatio)
                    .animate(CameraAnimation.Easing, 2000)
            )
            naverMap.locationTrackingMode = LocationTrackingMode.Face
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}