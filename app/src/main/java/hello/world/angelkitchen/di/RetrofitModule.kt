package hello.world.angelkitchen.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hello.world.angelkitchen.network.AngelServiceInstance
import hello.world.angelkitchen.network.ReverseGeoInstance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val baseAngelURL = "https://angelkitchen-1326.herokuapp.com/"
    private const val baseReverseGeoURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AngelAllDataType

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ReverseGeoType

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
    @ReverseGeoType
    fun provideGetRetroServiceInterfaceReverseGeo(
        @ReverseGeoType retrofit: Retrofit
    ): ReverseGeoInstance =
        retrofit.create(ReverseGeoInstance::class.java)

    @Singleton
    @Provides
    @ReverseGeoType
    fun provideGetRetroInstanceReverseGeo(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseReverseGeoURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}