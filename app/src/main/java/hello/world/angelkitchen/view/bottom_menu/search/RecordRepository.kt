package hello.world.angelkitchen.view.bottom_menu.search

import hello.world.angelkitchen.database.search_fragment.SearchFragmentDao
import hello.world.angelkitchen.database.search_fragment.SearchFragmentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecordRepository @Inject constructor(
    private val searchFragmentDao: SearchFragmentDao
) {

    fun getAllData(): Flow<List<SearchFragmentEntity>> =
        searchFragmentDao.getAllData()

    fun insertData(
        searchFragmentEntity: SearchFragmentEntity
    ) {
        searchFragmentDao.insertData(searchFragmentEntity)
    }

    fun deleteData(
        searchFragmentEntity: SearchFragmentEntity
    ) {
        searchFragmentDao.deleteData(searchFragmentEntity)
    }

    fun searchDatabase(searchQuery: String): Flow<List<SearchFragmentEntity>> =
        searchFragmentDao.searchDatabase(searchQuery)
}