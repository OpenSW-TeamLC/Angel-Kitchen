package hello.world.angelkitchen.view.bottom_menu.search.search_result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
) : ViewModel() {

    private val data = arrayListOf<SearchResultData>()

    private val _resultList = MutableLiveData<List<SearchResultData>>()
    val resultList: LiveData<List<SearchResultData>>
        get() = _resultList

    private val _searchResultPlace = MutableLiveData<SearchResultData>()
    val searchResultPlace: LiveData<SearchResultData>
        get() = _searchResultPlace

    fun getLiveDataObserver(): MutableLiveData<SearchResultData> = _searchResultPlace

    fun touchItem(resultData: SearchResultData) {
        _resultList.value = data
        _searchResultPlace.value = resultData
    }

    fun addPlace(resultData: SearchResultData) {
        data.add(resultData)
        _resultList.value = data
    }

    fun removeAllSearchResult() {
        data.clear()
        _resultList.value = data
    }
}