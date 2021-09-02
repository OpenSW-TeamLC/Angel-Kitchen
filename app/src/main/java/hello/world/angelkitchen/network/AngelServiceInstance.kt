package hello.world.angelkitchen.network

import hello.world.angelkitchen.data.angel_api.AngelScanKitchenData
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenDataBody
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenPostData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AngelServiceInstance {

    @POST("scankitchen")
    fun getAngelScanKitchenData(
        @Body angelScanKitchenPostData: AngelScanKitchenPostData
    ): Call<AngelScanKitchenDataBody>
}