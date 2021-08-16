package hello.world.angelkitchen.view.bottom_menu.bookmark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.view.bottom_menu.search.RecordData
import hello.world.angelkitchen.view.bottom_menu.search.RecordRepository
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
) : ViewModel() {

    val bookmarkDataList = MutableLiveData<List<BookmarkData>>()
    private val data = arrayListOf<BookmarkData>()

    fun showToastBookmark(bookmarkData: BookmarkData): Int =
        data.indexOf(bookmarkData)

    fun addBookmark(bookmarkData: BookmarkData) {
        data.add(bookmarkData)
        bookmarkDataList.value = data
    }

    fun removeAllBookmark() {
        data.clear()
        bookmarkDataList.value = data
    }
}