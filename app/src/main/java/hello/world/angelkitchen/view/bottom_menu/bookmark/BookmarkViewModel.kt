package hello.world.angelkitchen.view.bottom_menu.bookmark

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.database.bookmark_fragment.BookmarkFragmentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkFragmentRepository: BookmarkFragmentRepository
) : ViewModel() {

    private val _touchItemInfo = MutableLiveData<BookmarkFragmentEntity>()
    val touchItemInfo: LiveData<BookmarkFragmentEntity>
        get() = _touchItemInfo

    private val bookmarkDataList: LiveData<List<BookmarkFragmentEntity>> =
        bookmarkFragmentRepository.getAllData().asLiveData()

    fun touchItem(bookmarkFragmentEntity: BookmarkFragmentEntity) {
        _touchItemInfo.value = bookmarkFragmentEntity
    }

    fun getAllData() = bookmarkDataList

    fun deleteBookmark(number: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkFragmentRepository.deleteByNumber(number)
        }
    }
}