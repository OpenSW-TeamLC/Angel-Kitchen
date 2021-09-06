package hello.world.angelkitchen.view.bottom_menu.direction

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hello.world.angelkitchen.data.reverse_geo_api.*
import hello.world.angelkitchen.di.RetrofitModule
import hello.world.angelkitchen.network.NaverMapApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DirectionFragmentRepository @Inject constructor(
    @RetrofitModule.ReverseGeoType internal val naverMapApiReverseGeo: NaverMapApi,
    @RetrofitModule.GeoType private val naverMapApiGeo: NaverMapApi,
    @RetrofitModule.GetResultPathType private val naverMapGetResultPath: NaverMapApi
) {
    fun makeGeoApiCall(
        apiKeyId: String,
        apiKey: String,
        query: String,
        liveDataList: MutableLiveData<Addresse>
    ) {
        val call = naverMapApiGeo.getGeo(apiKeyId, apiKey, query)
        Log.d("test123", call.request().url.toString())
        call.enqueue(object : Callback<GeoApi> {
            override fun onResponse(call: Call<GeoApi>, response: Response<GeoApi>) {
                if(response.body() != null && response.isSuccessful)
                    liveDataList.postValue(response.body()?.addresses?.get(0))
                else
                    liveDataList.postValue(null)
            }

            override fun onFailure(call: Call<GeoApi>, t: Throwable) {

            }

        })
    }

    fun makeReverseGeoApiCall(
        apiKeyId: String,
        apiKey: String,
        coords: String,
        liveDataList: MutableLiveData<Result1>
    ) {
        val call = naverMapApiReverseGeo.getReverseGeo(apiKeyId, apiKey, coords)
        call.enqueue(object : Callback<ReverseGeoApi> {
            override fun onResponse(call: Call<ReverseGeoApi>, response: Response<ReverseGeoApi>) {
                if(response.body() != null && response.isSuccessful)
                    liveDataList.postValue(response.body()!!.results?.get(0))
                else
                    liveDataList.postValue(null)
            }

            override fun onFailure(call: Call<ReverseGeoApi>, t: Throwable) {

            }
        })
    }

    fun makeGetResultPathApiCall(
        apiKeyId: String,
        apiKey: String,
        start: String,
        goal: String,
        liveDataList: MutableLiveData<List<Result_path>>
    ) {
        val call = naverMapGetResultPath.getResultPath(apiKeyId, apiKey, start, goal)
        call.enqueue(object : Callback<GetResultPath> {
            override fun onResponse(call: Call<GetResultPath>, response: Response<GetResultPath>) {
                if(response.body() != null && response.isSuccessful)
                    liveDataList.postValue(response.body()?.route?.traoptimal!!)
                else
                    liveDataList.postValue(null)
            }

            override fun onFailure(call: Call<GetResultPath>, t: Throwable) {

            }

        })
    }
}