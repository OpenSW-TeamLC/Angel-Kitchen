package hello.world.angelkitchen.network

import hello.world.angelkitchen.data.reverse_geo_api.GeoApi
import hello.world.angelkitchen.data.reverse_geo_api.GetResultPath
import hello.world.angelkitchen.data.reverse_geo_api.Region
import hello.world.angelkitchen.data.reverse_geo_api.ReverseGeoApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverMapApi {
    @GET("v2/geocode")
    fun getGeo(
        @Query("X-NCP-APIGW-API-KEY-ID") apiKeyId: String,
        @Query("X-NCP-APIGW-API-KEY") apiKey: String,
        @Query("query") query: String
    ): Call<GeoApi>

    @GET("v2/gc")
    fun getReverseGeo(
        @Query("X-NCP-APIGW-API-KEY-ID") apiKeyId: String,
        @Query("X-NCP-APIGW-API-KEY") apiKey: String,
        @Query("coords") coords: String,
        @Query("output") output: String = "json",
        @Query("orders") orders: String = "addr"
    ): Call<ReverseGeoApi>

    @GET("v1/driving")
    fun getResultPath(
        @Query("X-NCP-APIGW-API-KEY-ID") apiKeyID: String,
        @Query("X-NCP-APIGW-API-KEY") apiKey: String,
        @Query("start") start: String,
        @Query("goal") goal: String,
    ): Call<GetResultPath>
}