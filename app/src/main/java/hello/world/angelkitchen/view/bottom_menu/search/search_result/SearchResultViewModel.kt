package hello.world.angelkitchen.view.bottom_menu.search.search_result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.database.bookmark_fragment.BookmarkFragmentEntity
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
) : ViewModel() {

    private val data = arrayListOf<BookmarkFragmentEntity>()

    private val _resultList = MutableLiveData<List<BookmarkFragmentEntity>>()
    val resultList: LiveData<List<BookmarkFragmentEntity>>
        get() = _resultList

    private val _searchResultPlace = MutableLiveData<BookmarkFragmentEntity>()
    val searchResultPlace: LiveData<BookmarkFragmentEntity>
        get() = _searchResultPlace

    fun touchItem(resultData: BookmarkFragmentEntity) {
        _resultList.value = data
        _searchResultPlace.value = resultData
        Log.d("testest", "${_searchResultPlace.value!!}")
    }

    fun addPlace(resultData: BookmarkFragmentEntity) {
        data.add(resultData)
        _resultList.value = data
    }

    fun removeAllSearchResult() {
        data.clear()
        _resultList.value = data
    }
}