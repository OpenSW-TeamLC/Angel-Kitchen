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
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : FragmentActivity(), OnMapReadyCallback{
    public var usercurrentlat:Double = 0.0
    public var usercurrentlong:Double=0.0
    public val markers = mutableListOf<Marker>()
    private var isFirstLocation = true;
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private var locationSource: FusedLocationSource? = null
    private lateinit var naverMap: NaverMap
    private var odsayService: ODsayService? = null
    private var jsonObject: JSONObject? = null
    private var mapObject: Map<*, *>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationSource =
            FusedLocationSource(this, LocationTrackingActivity.LOCATION_PERMISSION_REQUEST_CODE)
        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(37.5666102, 126.9783881), 16.0))
            .mapType(NaverMap.MapType.Basic)
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }
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
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.addOnLocationChangeListener { location ->
            //Toast.makeText(this, "${location.latitude},${location.longitude}", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "${isFirstLocation}", Toast.LENGTH_SHORT).show()
            if (isFirstLocation) {
                //레트로핏 객체 생성
                val APIKEY_ID = "uzlzuhd2pa"
                val APIKEY = "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc"
                val retrofit = Retrofit.Builder().
                baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/").
                addConverterFactory(GsonConverterFactory.create()).
                build()
                val api = retrofit.create(NaverAPI::class.java)
                val callgetPath = api.getPath(APIKEY_ID, APIKEY,"${location.longitude},${location.latitude}", "126.97822,37.55855")
                callgetPath.enqueue(object : Callback<ResultPath> {
                    override fun onResponse(
                        call: Call<ResultPath>,
                        response: Response<ResultPath>
                    ) {
                        var path_cords_list = response.body()?.route?.traoptimal
                        val path = PathOverlay()
                        val path_container : MutableList<LatLng>? = mutableListOf(LatLng(0.1,0.1))
                        if (path_cords_list != null) {
                            for(path_cords in path_cords_list){
                                for(path_cords_xy in path_cords?.path){
                                    //구한 경로를 하나씩 path_container에 추가해줌
                                    path_container?.add(LatLng(path_cords_xy[1], path_cords_xy[0]))
                                }
                            }
                            //더미원소 드랍후 path.coords에 path들을 넣어줌.
                            path.coords = path_container?.drop(1)!!
                            path.color = Color.RED
                            path.map = naverMap
                        }
                    }
                    override fun onFailure(call: Call<ResultPath>, t: Throwable) {
                        //TODO("Not yet implemented")
                    }

                })
                Toast.makeText(this, "${isFirstLocation}", Toast.LENGTH_SHORT).show()
            }
            isFirstLocation = false;
        }
        val uiSettings = naverMap.uiSettings
        uiSettings.isCompassEnabled = true
        val infoWindow = InfoWindow()

        val retrofitfood = Retrofit.Builder().
        baseUrl("http://api.data.go.kr/openapi/").
        addConverterFactory(GsonConverterFactory.create()).
        build()
        var path_lat: MutableList<String> = mutableListOf()
        var path_long: MutableList<String> = mutableListOf()
        val foodapi = retrofitfood.create(AngelKitchen::class.java)
        for(i in 0..15) {
            val foodlocation = foodapi.getPath(
                "VEAAk7E+AFl+ebvIIp8rYPoQ0+dqaJRy4NRnWbo2wju5lvbYzuhlA55ZDydaRcdaViJftJwWTQiFtjtdS2Kkiw==",
                i.toString(),
                "100",
                "json"
            )
            foodlocation.enqueue(object : Callback<FoodData> {
                override fun onResponse(
                    call: Call<FoodData>,
                    response: Response<FoodData>
                ) {
                    //Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
                    var foodposition: List<items>? = mutableListOf()
                    var theDataList: List<items> = ArrayList<items>()
                    if(response.body()?.response?.body?.items!=null) {
                        foodposition = response.body()?.response?.body?.items
                    }
                    if (foodposition != null) {
                        for(j in 0..(foodposition.size-1)){
                            val marker = Marker()
                            path_lat.add(foodposition[j].latitude)
                            path_long.add(foodposition[j].longitude)
                            marker.position=LatLng(path_lat[j].toDouble(),path_long[j].toDouble())
                            marker.tag = foodposition[j].fcltyNm
                            marker.setOnClickListener {
                                // 마커를 클릭할 때 정보창을 엶
                                infoWindow.open(marker)
                                true
                            }
                            markers.add(marker)
                        }
                    }
                    //Toast.makeText(this@MainActivity, response.body().toString(), Toast.LENGTH_LONG).show()
                }
                override fun onFailure(call: Call<FoodData>, t: Throwable) {
                }

            })
        }

        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return infoWindow.marker?.tag as CharSequence? ?: ""
            }
        }
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
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}