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
import timber.log.Timber
import javax.inject.Inject

class SearchResultRepository @Inject constructor(
    @RetrofitModule.AngelGetDataType private val angelServiceInstance: AngelServiceInstance
) {
    fun makeApi(
        angelSearchQuery: String,
        liveDataList: MutableLiveData<AngelGetSearchDataBody>
    ) {
        val call = angelServiceInstance.getAngelSearchKitchenData(angelSearchQuery)
        Timber.d("", call.request().url)
        call.enqueue(object : Callback<AngelGetSearchData> {
            override fun onResponse(
                call: Call<AngelGetSearchData>,
                response: Response<AngelGetSearchData>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Timber.d("Success")
                    liveDataList.postValue(response.body()?.body)
                } else Timber.d("Value Null")
            }

            override fun onFailure(call: Call<AngelGetSearchData>, t: Throwable) {
                Timber.d("Fail")
                liveDataList.postValue(null)
            }

        })
    }
}