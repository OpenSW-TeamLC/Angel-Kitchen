package hello.world.angelkitchen.view.bottom_menu.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hello.world.angelkitchen.data.angel_api.AngelAllData
import hello.world.angelkitchen.network.AngelServiceInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AngelAllDataRepository @Inject constructor(
    private val angelServiceInstance: AngelServiceInstance
) {
    fun makeApiCall(
        liveDataList: MutableLiveData<String>
    ) {
        val call = angelServiceInstance.getAllData()
        call.enqueue(object : Callback<AngelAllData> {
            override fun onResponse(call: Call<AngelAllData>, response: Response<AngelAllData>) {
                liveDataList.postValue(response.body()?.fcltyNm!!)
                Log.d("AngelAllDataRepository", "success")
            }

            override fun onFailure(call: Call<AngelAllData>, t: Throwable) {
                liveDataList.postValue(null)
                Log.d("AngelAllDataRepository", "fail")
            }

        })
    }
}