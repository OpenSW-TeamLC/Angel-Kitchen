package hello.world.angelkitchen.view.bottom_menu.search.search_result.bottom_sheet

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.database.bookmark_fragment.BookmarkFragmentEntity
import hello.world.angelkitchen.view.bottom_menu.bookmark.BookmarkFragmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetFragmentViewModel @Inject constructor(
    private val bookmarkFragmentRepository: BookmarkFragmentRepository
) : ViewModel() {

    private val _resultPlaceInfo = MutableLiveData<BookmarkFragmentEntity>()
    val resultPlaceInfo: LiveData<BookmarkFragmentEntity>
        get() = _resultPlaceInfo

    private val _btnAddressShare = MutableLiveData<String>()
    val btnAddressShare: LiveData<String>
        get() = _btnAddressShare

    fun setPlaceInfo(searchResultData: BookmarkFragmentEntity) {
        _resultPlaceInfo.value = searchResultData
    }

    fun setAddress(address: String) {
        _btnAddressShare.value = address
    }

    fun insertBookmark(bookmarkFragmentEntity: BookmarkFragmentEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkFragmentRepository.insertData(bookmarkFragmentEntity)
        }
    }

    fun deleteByNumber(number: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkFragmentRepository.deleteByNumber(number)
        }
    }

    fun getAllLikeData() = bookmarkFragmentRepository.getAllLikeData().asLiveData()
}