package hello.world.angelkitchen.view.bottom_menu.bookmark

import hello.world.angelkitchen.database.bookmark_fragment.BookmarkFragmentDao
import hello.world.angelkitchen.database.bookmark_fragment.BookmarkFragmentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkFragmentRepository @Inject constructor(
    private val bookmarkFragmentDao: BookmarkFragmentDao
) {

    fun getAllData(): Flow<List<BookmarkFragmentEntity>> =
        bookmarkFragmentDao.getAllData()

    suspend fun insertData(
        bookmarkFragmentEntity: BookmarkFragmentEntity
    ) {
        bookmarkFragmentDao.insertData(bookmarkFragmentEntity)
    }

    suspend fun deleteByNumber(
        number: String
    ) {
        bookmarkFragmentDao.deleteByNumber(number)
    }

    fun getAllLikeData() = bookmarkFragmentDao.getAllLikeData()
}