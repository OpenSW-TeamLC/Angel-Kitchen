package hello.world.angelkitchen
import android.content.ContentValues.TAG
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.core.text.isDigitsOnly
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
import ted.gun0912.clustering.naver.TedNaverClustering

class MainActivity : FragmentActivity(), OnMapReadyCallback{
    public val markers = mutableListOf<Marker>()
    private var isFirstLocation = true;
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private var locationSource: FusedLocationSource? = null
    private lateinit var naverMap: NaverMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationSource =
            FusedLocationSource(this, LocationTrackingActivity.LOCATION_PERMISSION_REQUEST_CODE)
        //naver Map 초기 설정
        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(37.5666102, 126.9783881), 16.0))
            .mapType(NaverMap.MapType.Basic)
        val fm = supportFragmentManager
        //naver Map 렌더링
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    //사용자 위치 추척 함수
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource?.onRequestPermissionsResult(requestCode, permissions,
                grantResults) == true
        ) {
            if (!locationSource?.isActivated!!) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //naver Map이 렌더링 완료되면 실행 되는 함수
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        //naver Map 사용자 위치 추적
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.addOnLocationChangeListener { location ->
            //Toast.makeText(this, "${location.latitude},${location.longitude}", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "${isFirstLocation}", Toast.LENGTH_SHORT).show()
            if (isFirstLocation) {//자신의 위치로 첫 경로검색 후 재검색 방지
                //naver Directions 5 API 레트로핏 객체 생성
                val APIKEY_ID = "uzlzuhd2pa"
                val APIKEY = "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc"
                val retrofit = Retrofit.Builder().
                baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/").
                addConverterFactory(GsonConverterFactory.create()).
                build()
                val api = retrofit.create(NaverAPI::class.java)
                //API 응답값 받기
                val callgetPath = api.getPath(APIKEY_ID, APIKEY,"${location.longitude},${location.latitude}", "126.97822,37.55855")
                callgetPath.enqueue(object : Callback<ResultPath> {
                    override fun onResponse(//응답값 성공
                        call: Call<ResultPath>,
                        response: Response<ResultPath>
                    ) {
                        var path_cords_list = response.body()?.route?.traoptimal //JSON 응답값 중 traoptimal(경로) 받아오기
                        val path = PathOverlay() //경로 객체 생성
                        val path_container : MutableList<LatLng>? = mutableListOf(LatLng(0.1,0.1)) //LatLng MutableList 생성
                        if (path_cords_list != null) {
                            for(path_cords in path_cords_list){
                                for(path_cords_xy in path_cords?.path){
                                    //구한 경로를 하나씩 path_container에 추가해줌
                                    path_container?.add(LatLng(path_cords_xy[1], path_cords_xy[0]))
                                }
                            }
                            //더미원소 드랍후 path.coords에 path들을 넣어줌.
                            path.coords = path_container?.drop(1)!!
                            //경로 색깔 결정
                            path.color = Color.RED
                            //map에 경로 렌더링
                            path.map = naverMap
                        }
                    }
                    override fun onFailure(call: Call<ResultPath>, t: Throwable) {//실패시
                        //TODO("Not yet implemented")
                    }

                })
            }
            isFirstLocation = false;//false로 설정하여 다시 if문이 못 돌아가게 설정
        }
        //naverMap UI 허용
        val uiSettings = naverMap.uiSettings
        uiSettings.isCompassEnabled = true
        var i:Int
        val infoWindow = InfoWindow()
        //공공데이터 포털 급식소 API 레트로핏 객체 생성
        val retrofitfood = Retrofit.Builder().
        baseUrl("http://api.data.go.kr/openapi/").
        addConverterFactory(GsonConverterFactory.create()).
        build()
        var path_lat: MutableList<String> = mutableListOf() //latitude MutableList 생성
        var path_long: MutableList<String> = mutableListOf()//longitude MutableList 생성
        val foodapi = retrofitfood.create(AngelKitchen::class.java)
        for(i in 0..15) {
            //무료급식소 API 응답값 받아오기
            val foodlocation = foodapi.getPath(
                "VEAAk7E+AFl+ebvIIp8rYPoQ0+dqaJRy4NRnWbo2wju5lvbYzuhlA55ZDydaRcdaViJftJwWTQiFtjtdS2Kkiw==",
                i.toString(),
                "100",
                "json"
            )
            foodlocation.enqueue(object : Callback<FoodData> {
                override fun onResponse(// 응답 성공시
                    call: Call<FoodData>,
                    response: Response<FoodData>
                ) {
                    //Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
                    var foodposition: List<items>? = mutableListOf()//무료급식소 API 응답값인 무료급식소 정보를 받기 위한 List 생성
                    var theDataList: List<items> = ArrayList<items>()
                    if(response.body()?.response?.body?.items!=null) {
                        foodposition = response.body()?.response?.body?.items//무료급식소 API 응답값인 무료급식소 정보를 받아오기
                    }
                    if (foodposition != null) {
                        for(j in 0..(foodposition.size-1)) {
                            val marker = Marker()//마커 객체 생성
                            var path_la: Double? =
                                (foodposition[j].latitude).toDoubleOrNull()//latitude List에 무료급식소의 latitude 입력
                            var path_lon: Double? =
                                (foodposition[j].longitude).toDoubleOrNull()//longitude List에 무료급식소의 longitude 입력
                            if (path_la != null && path_lon != null) {//null check
                                marker.position = LatLng(
                                    path_la,
                                    path_lon
                                )//마커 위치 생성
                                marker.tag =
                                    foodposition[j]?.fcltyNm//마커 입력시 나오는 값 설정(현재 설정된 값은 무료급식소 이름)
                                marker.setOnClickListener {// 마커를 클릭할 때 정보창을 엶
                                    infoWindow.open(marker)
                                    true
                                }
                                markers.add(marker)//marker List에 설정된 마커 설정
                            }
                        }
                    }
                    //Toast.makeText(this@MainActivity, response.body().toString(), Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<FoodData>, t: Throwable) {//응답 실패시
                }

            })
        }

        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return infoWindow.marker?.tag as CharSequence? ?: ""
            }
        }
        // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
        naverMap.addOnCameraChangeListener { reason, animated ->
            //freeActiveMarkers(markers1)
            val currentPosition = getCurrentPosition(naverMap)//현재 카메라 위치 받아오기
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
        TedNaverClustering.with<NaverItem>(this, naverMap)
            .items(getItems())
            .make()
    }

    private fun getItems(): Collection<NaverItem> {
        return ArrayList<NaverItem>().apply {
            for(ma in markers){
                val temp = NaverItem(ma.position)
                add(temp)
            }
        }
    }

    private var markersPosition: Vector<LatLng>? = null
    private var activeMarkers: Vector<Marker>? = null
    //현재 카메라 Position 받아오기
    fun getCurrentPosition(naverMap: NaverMap): LatLng? {
        val cameraPosition = naverMap.cameraPosition
        return LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude)
    }

    // 선택한 마커의 위치가 가시거리(카메라가 보고있는 위치 반경 3km 내)에 있는지 확인
    val REFERANCE_LAT = 1 / 109.958489129649955
    val REFERANCE_LNG = 1 / 88.74
    val REFERANCE_LAT_X3 = 3 / 109.958489129649955//마커 가시 반경 설정(현재 3km)
    val REFERANCE_LNG_X3 = 3 / 88.74//마커 가시 반경 설정(현재 3km)
    fun withinSightMarker(currentPosition: LatLng, markerPosition: LatLng): Boolean {
        val withinSightMarkerLat =
            Math.abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3
        val withinSightMarkerLng =
            Math.abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3
        return withinSightMarkerLat && withinSightMarkerLng
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}