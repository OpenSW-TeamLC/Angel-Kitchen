package hello.world.angelkitchen.database.search_fragment

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SearchFragmentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SearchFragmentDatabase : RoomDatabase() {

    abstract fun getSearchFragmentDao(): SearchFragmentDao

    companion object {
        private var INSTANCE: SearchFragmentDatabase? = null

        fun getSearchFragmentInstance(context: Context): SearchFragmentDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    SearchFragmentDatabase::class.java,
                    "SearchFragmentDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}