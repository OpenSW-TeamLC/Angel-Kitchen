package hello.world.angelkitchen.view.bottom_menu.search

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {

    private val data = arrayListOf<RecordData>()

    private val _recordDataList = MutableLiveData<List<RecordData>>()
    val recordDataList: LiveData<List<RecordData>>
        get() = _recordDataList

    private val _sharePlace = MutableLiveData<String>()
    val sharePlace: LiveData<String>
        get() = _sharePlace

    fun touchItem(recordData: RecordData) {
        recordData.isClicked = !recordData.isClicked
        _recordDataList.value = data
        _sharePlace.value = recordData.recordText
    }

    fun addRecord(recordData: RecordData) {
        data.add(recordData)
        _recordDataList.value = data
    }

    fun deleteRecord(recordData: RecordData) {
        data.remove(recordData)
        _recordDataList.value = data
    }
}