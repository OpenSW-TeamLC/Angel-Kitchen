package hello.world.angelkitchen.database.bookmark_fragment

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkFragmentDao {

    @Query("SELECT * FROM frag_bookmark")
    fun getAllData(): Flow<List<BookmarkFragmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(word: BookmarkFragmentEntity)

    @Delete
    suspend fun deleteData(word: BookmarkFragmentEntity)

    @Query("DELETE FROM frag_bookmark WHERE number = :number")
    suspend fun deleteByNumber(number: String)

    @Query("SELECT * FROM frag_bookmark WHERE `like`")
    fun getAllLikeData(): Flow<List<BookmarkFragmentEntity>>
}