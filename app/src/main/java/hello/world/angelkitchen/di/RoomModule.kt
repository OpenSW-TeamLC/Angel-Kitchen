package hello.world.angelkitchen.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hello.world.angelkitchen.database.search_fragment.SearchFragmentDao
import hello.world.angelkitchen.database.search_fragment.SearchFragmentDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideGetAppDatabase(
        @ApplicationContext applicationContext: Context
    ): SearchFragmentDatabase =
        SearchFragmentDatabase.getSearchFragmentInstance(applicationContext)

    @Singleton
    @Provides
    fun provideGetAppDao(
        searchFragmentDatabase: SearchFragmentDatabase
    ): SearchFragmentDao =
        searchFragmentDatabase.getSearchFragmentDao()
}