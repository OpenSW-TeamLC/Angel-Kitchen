package hello.world.angelkitchen.view.bottom_menu.search.search_result.bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.view.bottom_menu.search.search_result.SearchResultData
import javax.inject.Inject

@HiltViewModel
class BottomSheetFragmentViewModel @Inject constructor(
) : ViewModel() {

    private val data = arrayListOf<SearchResultData>()

    private val _resultPlaceInfo = MutableLiveData<SearchResultData>()
    val resultPlaceInfo: LiveData<SearchResultData>
        get() = _resultPlaceInfo

    fun setPlaceInfo(searchResultData: SearchResultData) {
        _resultPlaceInfo.value = searchResultData
    }
}