package hello.world.angelkitchen

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
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.widget.ZoomControlView
import java.util.concurrent.Executor

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
        marker1.map = naverMap
        marker2.position = LatLng(37.5670135, 126.9783740)
        marker2.map = naverMap
        marker3.position = LatLng(37.5680135, 126.9783740)
        marker3.map = naverMap

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

        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return infoWindow.marker?.tag as CharSequence? ?: ""
            }
        }

    }
}

