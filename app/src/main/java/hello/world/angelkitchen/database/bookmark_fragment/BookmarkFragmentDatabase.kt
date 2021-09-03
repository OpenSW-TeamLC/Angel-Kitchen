package hello.world.angelkitchen.database.bookmark_fragment

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration

@Database(
    entities = [BookmarkFragmentEntity::class],
    version = 2,
    exportSchema = false
)
abstract class BookmarkFragmentDatabase : RoomDatabase() {

    abstract fun getBookmarkFragmentDao(): BookmarkFragmentDao

    companion object {
        private var INSTANCE: BookmarkFragmentDatabase? = null

        fun getSearchFragmentInstance(context: Context): BookmarkFragmentDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkFragmentDatabase::class.java,
                    "BookmarkFragmentDB"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}