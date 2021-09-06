package hello.world.angelkitchen.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hello.world.angelkitchen.database.bookmark_fragment.BookmarkFragmentDao
import hello.world.angelkitchen.database.bookmark_fragment.BookmarkFragmentDatabase
import hello.world.angelkitchen.database.search_fragment.SearchFragmentDao
import hello.world.angelkitchen.database.search_fragment.SearchFragmentDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideGetAppDatabaseSearchFragment(
        @ApplicationContext applicationContext: Context
    ): SearchFragmentDatabase =
        SearchFragmentDatabase.getSearchFragmentInstance(applicationContext)

    @Singleton
    @Provides
    fun provideGetAppDaoSearchFragment(
        searchFragmentDatabase: SearchFragmentDatabase
    ): SearchFragmentDao =
        searchFragmentDatabase.getSearchFragmentDao()

    @Singleton
    @Provides
    fun provideGetAppDatabaseBookmarkFragment(
            @ApplicationContext applicationContext: Context
        ): BookmarkFragmentDatabase =
    BookmarkFragmentDatabase.getSearchFragmentInstance(applicationContext)

    @Singleton
    @Provides
    fun provideGetAppDaoBookmarkFragment(
        bookmarkFragmentDatabase: BookmarkFragmentDatabase
    ): BookmarkFragmentDao =
        bookmarkFragmentDatabase.getBookmarkFragmentDao()
}