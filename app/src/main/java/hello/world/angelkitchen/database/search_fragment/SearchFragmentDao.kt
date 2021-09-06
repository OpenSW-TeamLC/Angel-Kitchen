package hello.world.angelkitchen.database.search_fragment

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchFragmentDao {

    @Query("SELECT * FROM frag_search ORDER BY id DESC")
    fun getAllData(): Flow<List<SearchFragmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(word: SearchFragmentEntity)

    @Delete
    fun deleteData(word: SearchFragmentEntity)

    @Query("SELECT * FROM frag_search WHERE word LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<SearchFragmentEntity>>
}