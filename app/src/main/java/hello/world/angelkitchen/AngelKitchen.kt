package hello.world.angelkitchen
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AngelKitchen {
    @GET("tn_pubr_public_free_mlsv_api")
    fun getPath(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: String,
        @Query("numOfRows") numOfRows: String,
        @Query("type") type: String
    ): Call<FoodData>
}