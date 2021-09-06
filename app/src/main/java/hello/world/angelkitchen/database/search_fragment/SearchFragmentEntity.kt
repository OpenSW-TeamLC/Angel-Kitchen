package hello.world.angelkitchen.database.search_fragment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "frag_search")
class SearchFragmentEntity(
    @ColumnInfo(name = "word")
    val preSearchWord: String
) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id: Int = 0
}