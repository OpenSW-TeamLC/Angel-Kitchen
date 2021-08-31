package hello.world.angelkitchen.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hello.world.angelkitchen.network.AngelServiceInstance
import hello.world.angelkitchen.network.NaverMapApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val baseAngelURL = "https://angelkitchen-1326.herokuapp.com/"
    private const val baseGeoURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/"
    private const val baseReverseGeoURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/"
    private const val baseGetResultPathURL = "https://naveropenapi.apigw.ntruss.com/map-direction/"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AngelAllDataType

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GeoType

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ReverseGeoType

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GetResultPathType

    @Singleton
    @Provides
    @AngelAllDataType
    fun provideGetRetroServiceInterfaceSearchFragment(
        @AngelAllDataType retrofit: Retrofit
    ): AngelServiceInstance =
        retrofit.create(AngelServiceInstance::class.java)

    @Singleton
    @Provides
    @AngelAllDataType
    fun provideGetRetroInstanceSearchFragment(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseAngelURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @GeoType
    fun provideGetRetroServiceInterfaceGeo(
        @GeoType retrofit: Retrofit
    ): NaverMapApi =
        retrofit.create(NaverMapApi::class.java)

    @Singleton
    @Provides
    @GeoType
    fun provideGetRetroInstanceGeo(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseGeoURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @ReverseGeoType
    fun provideGetRetroServiceInterfaceReverseGeo(
        @ReverseGeoType retrofit: Retrofit
    ): NaverMapApi =
        retrofit.create(NaverMapApi::class.java)

    @Singleton
    @Provides
    @ReverseGeoType
    fun provideGetRetroInstanceReverseGeo(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseReverseGeoURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @GetResultPathType
    fun provideGetRetroServiceInterfaceGetResultPath(
        @GetResultPathType retrofit: Retrofit
    ): NaverMapApi =
        retrofit.create(NaverMapApi::class.java)

    @Singleton
    @Provides
    @GetResultPathType
    fun provideGetRetroInstanceGetResultPath(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseGetResultPathURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}