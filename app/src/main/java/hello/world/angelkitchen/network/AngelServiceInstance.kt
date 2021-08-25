package hello.world.angelkitchen.network

import hello.world.angelkitchen.data.angel_api.AngelAllData
import retrofit2.Call
import retrofit2.http.GET

interface AngelServiceInstance {
    @GET("readdata")
    fun getAllData(): Call<AngelAllData>
}