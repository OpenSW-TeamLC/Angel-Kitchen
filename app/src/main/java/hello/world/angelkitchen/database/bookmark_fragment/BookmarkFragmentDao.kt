package hello.world.angelkitchen.database.bookmark_fragment

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkFragmentDao {

    @Query("SELECT * FROM frag_bookmark")
    fun getAllData(): Flow<List<BookmarkFragmentEntity>>

    @Insert
    fun insertData(word: BookmarkFragmentEntity)

    @Delete
    fun deleteData(word: BookmarkFragmentEntity)
}