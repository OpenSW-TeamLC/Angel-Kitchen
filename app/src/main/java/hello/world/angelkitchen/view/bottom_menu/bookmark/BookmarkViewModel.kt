package hello.world.angelkitchen.view.bottom_menu.bookmark

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.view.bottom_menu.search.RecordRepository
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: RecordRepository
) : ViewModel() {
    val bookmarkDataList = arrayListOf<BookmarkData>()

    fun showToastBookmark(bookmarkData: BookmarkData) : Int = bookmarkDataList.indexOf(bookmarkData)

    fun addBookmark(bookmarkData: BookmarkData) {
        bookmarkDataList.add(bookmarkData)
    }

    fun removeAllBookmark() {
        bookmarkDataList.clear()
    }
}