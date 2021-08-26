package hello.world.angelkitchen.view.bottom_menu.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.database.bookmark_fragment.BookmarkFragmentEntity
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkFragmentRepository: BookmarkFragmentRepository
) : ViewModel() {

    val bookmarkDataList: LiveData<List<BookmarkFragmentEntity>> =
        bookmarkFragmentRepository.getAllData().asLiveData()

    fun getAllData() = bookmarkDataList

    suspend fun removeAllBookmark(bookmarkFragmentEntity: BookmarkFragmentEntity) {
        bookmarkFragmentRepository.deleteData(bookmarkFragmentEntity)
    }
}