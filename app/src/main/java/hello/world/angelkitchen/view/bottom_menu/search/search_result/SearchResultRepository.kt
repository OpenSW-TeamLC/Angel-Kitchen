package hello.world.angelkitchen.view.bottom_menu.search.search_result

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hello.world.angelkitchen.data.angel_api.AngelGetSearchData
import hello.world.angelkitchen.data.angel_api.AngelGetSearchDataBody
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenDataBody
import hello.world.angelkitchen.di.RetrofitModule
import hello.world.angelkitchen.network.AngelServiceInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchResultRepository @Inject constructor(
    @RetrofitModule.AngelGetDataType private val angelServiceInstance: AngelServiceInstance
) {
    fun makeApi(
        angelSearchQuery: String,
        liveDataList: MutableLiveData<AngelGetSearchDataBody>
    ) {
        val call = angelServiceInstance.getAngelSearchKitchenData(angelSearchQuery)
        Log.d("SearchResultRepository", "${call.request().url}")
        call.enqueue(object : Callback<AngelGetSearchData> {
            override fun onResponse(
                call: Call<AngelGetSearchData>,
                response: Response<AngelGetSearchData>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("SearchResultRepository", "Success")
                    liveDataList.postValue(response.body()?.body)
                } else Log.d("SearchResultRepository", "Value Null")
            }

            override fun onFailure(call: Call<AngelGetSearchData>, t: Throwable) {
                Log.d("SearchResultRepository", "Fail")
                liveDataList.postValue(null)
            }

        })
    }
}