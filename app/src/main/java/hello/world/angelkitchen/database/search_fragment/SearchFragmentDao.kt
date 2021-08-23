package hello.world.angelkitchen.database.search_fragment

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchFragmentDao {

    @Query("SELECT * FROM frag_search")
    fun getAllData(): Flow<List<SearchFragmentEntity>>

    @Insert
    fun insertData(word: SearchFragmentEntity)

    @Delete
    fun deleteData(word: SearchFragmentEntity)
}