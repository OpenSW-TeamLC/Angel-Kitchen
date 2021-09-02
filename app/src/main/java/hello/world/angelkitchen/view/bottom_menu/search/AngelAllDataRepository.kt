package hello.world.angelkitchen.view.bottom_menu.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenData
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenPostData
import hello.world.angelkitchen.di.RetrofitModule
import hello.world.angelkitchen.network.AngelServiceInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AngelAllDataRepository @Inject constructor(
    @RetrofitModule.AngelThreeDataType private val angelServiceInstance: AngelServiceInstance
) {
    fun makeApiCall(
        angelScanKitchenPostData: AngelScanKitchenPostData,
        liveDataList: MutableLiveData<String>
    ) {
        val call = angelServiceInstance.getAngelScanKitchenData(angelScanKitchenPostData)

    }
}