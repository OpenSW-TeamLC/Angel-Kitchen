package hello.world.angelkitchen.database.bookmark_fragment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "frag_bookmark")
class BookmarkFragmentEntity(
    val imgPath: String,
    val place: String,
    val address: String,
    val number: String,
    var like: Boolean,
    val target: String,
    val startTime: String,
    val day: String,
    val start: String,
    val lat: Double,
    val lon: Double
) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id: Int = 0
}