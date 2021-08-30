package hello.world.angelkitchen.view.bottom_menu.direction

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hello.world.angelkitchen.data.reverse_geo_api.Land
import hello.world.angelkitchen.data.reverse_geo_api.Result1
import hello.world.angelkitchen.data.reverse_geo_api.ReverseGeoApi
import hello.world.angelkitchen.di.RetrofitModule
import hello.world.angelkitchen.network.ReverseGeoInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DirectionFragmentRepository @Inject constructor(
    @RetrofitModule.ReverseGeoType private val reverseGeoInstance: ReverseGeoInstance
) {
    fun makeApiCall(
        apiKeyId: String,
        apiKey: String,
        coords: String,
        liveDataList: MutableLiveData<Result1>
    ) {
        val call = reverseGeoInstance.getReverseGeo(apiKeyId, apiKey, coords)
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
}