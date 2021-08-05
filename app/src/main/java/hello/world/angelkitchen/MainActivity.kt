package hello.world.angelkitchen

import android.graphics.Color
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.widget.ZoomControlView
import java.util.*
import java.util.concurrent.Executor
import com.naver.maps.map.CameraPosition

import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMap.OnCameraChangeListener
import com.naver.maps.map.overlay.*


class MainActivity : FragmentActivity(), OnMapReadyCallback {
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private val locationSource: FusedLocationSource? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(37.5666102, 126.9783881), 16.0))
            .mapType(NaverMap.MapType.Basic)
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance(options).also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        val uiSettings = naverMap.uiSettings
        uiSettings.isCompassEnabled = true
        val infoWindow = InfoWindow()
        val marker1 = Marker()
        val marker2 = Marker()
        val marker3 = Marker()

        marker1.position = LatLng(37.5770135, 126.9783740)
        //marker1.map = naverMap
        marker2.position = LatLng(37.5670135, 126.9783740)
        //marker2.map = naverMap
        marker3.position = LatLng(37.5680135, 126.9783740)
        //marker3.map = naverMap

        naverMap.setOnMapClickListener { coord, point -> infoWindow.close() }

        marker1.tag = "마커 1"
        marker1.setOnClickListener {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(marker1)
            true
        }

        marker2.tag = "마커 2"
        marker2.setOnClickListener {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(marker2)
            true
        }
        val markers= listOf(marker1.position,marker2.position,marker3.position)
        val markers1=listOf(marker1,marker2,marker3)
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return infoWindow.marker?.tag as CharSequence? ?: ""
            }
        }
        naverMap.addOnCameraChangeListener { reason, animated ->
            freeActiveMarkers()
            // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
            val currentPosition = getCurrentPosition(naverMap)
            for (markerPosition in markers1) {
                if (!withinSightMarker(LatLng(37.5680135, 126.9783740), markerPosition.position)) continue
                val marker = Marker()
                marker.position = markerPosition.position
                marker.tag=markerPosition.tag
                marker.setOnClickListener {
                    // 마커를 클릭할 때 정보창을 엶
                    infoWindow.open(marker)
                    true
                }
                marker.map = naverMap
            }
        }
        val path = PathOverlay()
        path.coords = listOf(
            LatLng(37.57152, 126.97714),
            LatLng(37.56607, 126.98268),
            LatLng(37.56445, 126.97707),
            LatLng(37.55855, 126.97822)
        )
        path.width=30
        path.color = Color.RED
        path.map = naverMap

    }
    private var markersPosition: Vector<LatLng>? = null
    private var activeMarkers: Vector<Marker>? = null
    fun getCurrentPosition(naverMap: NaverMap): LatLng? {
        val cameraPosition = naverMap.cameraPosition
        return LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude)
    }

    // 선택한 마커의 위치가 가시거리(카메라가 보고있는 위치 반경 3km 내)에 있는지 확인
    val REFERANCE_LAT = 1 / 109.958489129649955
    val REFERANCE_LNG = 1 / 88.74
    val REFERANCE_LAT_X3 = 3 / 109.958489129649955
    val REFERANCE_LNG_X3 = 3 / 88.74
    fun withinSightMarker(currentPosition: LatLng, markerPosition: LatLng): Boolean {
        val withinSightMarkerLat =
            Math.abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3
        val withinSightMarkerLng =
            Math.abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3
        return withinSightMarkerLat && withinSightMarkerLng
    }
    private fun freeActiveMarkers() {
        if (activeMarkers == null) {
            activeMarkers = Vector()
            return
        }
        for (activeMarker in activeMarkers!!) {
            activeMarker.map = null
        }
        activeMarkers = Vector()
    }
}

