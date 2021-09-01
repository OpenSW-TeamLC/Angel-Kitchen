package hello.world.angelkitchen

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AngelKitchenLocation {
    @Headers("accept: application/json")
    @GET("searchkitchen")
    fun getPath(
        @Query("kitchenName") kitchenName: String,
        @Query("kitchenPlace") kitchenPlace: String
    ): Call<FoodLocation>
}