package hello.world.angelkitchen.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hello.world.angelkitchen.network.AngelServiceInstance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val baseURL = "https://angelkitchen-1326.herokuapp.com/"

    @Singleton
    @Provides
    fun provideGetRetroServiceInterfaceSearchFragment(
        retrofit: Retrofit
    ): AngelServiceInstance =
        retrofit.create(AngelServiceInstance::class.java)

    @Singleton
    @Provides
    fun provideGetRetroInstanceSearchFragment(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}