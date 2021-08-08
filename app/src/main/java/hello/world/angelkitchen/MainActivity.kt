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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : FragmentActivity(), OnMapReadyCallback{
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
        var i=0;
        var sum=0;
        for(i in 1..3){
            sum+=i
        }
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
        val markere = mutableListOf<Marker>()
        markere.add(marker1)
        markere.add(marker2)
        markere.add(marker3)
        val markers = mutableListOf<Marker>()
        for(i in 0..2){
            markers.add(markere[i])
        }
        markers.add(marker1)
        markers.add(marker2)
        val markers1=listOf(marker1,marker2,marker3)
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return infoWindow.marker?.tag as CharSequence? ?: ""
            }
        }
        val marker = Marker()
        naverMap.addOnCameraChangeListener { reason, animated ->
            //freeActiveMarkers(markers1)
            // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
            val currentPosition = getCurrentPosition(naverMap)
            val cameraPosition = naverMap.cameraPosition
            for (markerPosition in markers) {
                if (withinSightMarker(LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude), markerPosition.position)) {
                    markerPosition.map = naverMap
                }
                else{
                    markerPosition.map = null
                }
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
        val APIKEY_ID = ""
        val APIKEY = ""
        //레트로핏 객체 생성
        val retrofit = Retrofit.Builder().
        baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/").
        addConverterFactory(GsonConverterFactory.create()).
        build()

        val api = retrofit.create(NaverAPI::class.java)
        //근처에서 길찾기
        val callgetPath = api.getPath(APIKEY_ID, APIKEY,"126.97714,37.57152", "126.97822,37.55855")

        callgetPath.enqueue(object : Callback<ResultPath> {
            override fun onResponse(
                call: Call<ResultPath>,
                response: Response<ResultPath>
            ) {
                var path_cords_list = response.body()?.route?.traoptimal
                //경로 그리기 응답바디가 List<List<Double>> 이라서 2중 for문 썼음
                val path = PathOverlay()
                //MutableList에 add 기능 쓰기 위해 더미 원소 하나 넣어둠
                val path_container : MutableList<LatLng>? = mutableListOf(LatLng(0.1,0.1))
                for(path_cords in path_cords_list!!){
                    for(path_cords_xy in path_cords?.path){
                        //구한 경로를 하나씩 path_container에 추가해줌
                        path_container?.add(LatLng(path_cords_xy[1], path_cords_xy[0]))
                    }
                }
                //더미원소 드랍후 path.coords에 path들을 넣어줌.
                path.coords = path_container?.drop(1)!!
                path.color = Color.RED
                path.map = naverMap

                //경로 시작점으로 화면 이동
                if(path.coords != null) {
                    val cameraUpdate = CameraUpdate.scrollTo(path.coords[0]!!)
                        .animate(CameraAnimation.Fly, 3000)
                    naverMap.moveCamera(cameraUpdate)

                    Toast.makeText(this@MainActivity, "경로 안내가 시작됩니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResultPath>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
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
    val REFERANCE_LAT_X3 = 1 / 109.958489129649955
    val REFERANCE_LNG_X3 = 1 / 88.74
    fun withinSightMarker(currentPosition: LatLng, markerPosition: LatLng): Boolean {
        val withinSightMarkerLat =
            Math.abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3
        val withinSightMarkerLng =
            Math.abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3
        return withinSightMarkerLat && withinSightMarkerLng
    }
}

