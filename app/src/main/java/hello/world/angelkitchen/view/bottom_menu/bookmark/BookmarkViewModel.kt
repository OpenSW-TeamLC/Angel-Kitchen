package hello.world.angelkitchen.view.bottom_menu.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.view.bottom_menu.search.RecordData
import hello.world.angelkitchen.view.bottom_menu.search.RecordRepository
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
) : ViewModel() {

    private val data = arrayListOf<BookmarkData>()

    private val _bookmarkDataList = MutableLiveData<List<BookmarkData>>()
    val bookmarkDataList: LiveData<List<BookmarkData>> = _bookmarkDataList

    fun showToastBookmark(bookmarkData: BookmarkData): Int =
        data.indexOf(bookmarkData)

    fun addBookmark(bookmarkData: BookmarkData) {
        data.add(bookmarkData)
        _bookmarkDataList.value = data
    }

    fun removeAllBookmark() {
        data.clear()
        _bookmarkDataList.value = data
    }
}