package hello.world.angelkitchen.view.main

import androidx.lifecycle.MutableLiveData
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenData
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenDataBody
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenPostData
import hello.world.angelkitchen.di.RetrofitModule
import hello.world.angelkitchen.network.AngelServiceInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class MainActivityRepository @Inject constructor(
    @RetrofitModule.AngelGetDataType private val angelServiceInstance: AngelServiceInstance
) {
    fun makeApi(
        angelScanKitchenPostData: AngelScanKitchenPostData,
        liveDataList: MutableLiveData<AngelScanKitchenData>
    ) {
        val call = angelServiceInstance.getAngelScanKitchenData(angelScanKitchenPostData)
        Timber.d(call.request().url.toString())
        call.enqueue(object : Callback<AngelScanKitchenDataBody> {
            override fun onResponse(
                call: Call<AngelScanKitchenDataBody>,
                response: Response<AngelScanKitchenDataBody>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    liveDataList.postValue(response.body()?.body)
                    Timber.d("success")
                    Timber.d("body", response.body()?.body?.items)
                }
            }

            override fun onFailure(call: Call<AngelScanKitchenDataBody>, t: Throwable) {
                liveDataList.postValue(null)
                Timber.d("fail@" + t.message)
            }
        })
    }
}