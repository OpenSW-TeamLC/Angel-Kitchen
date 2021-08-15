package hello.world.angelkitchen.view.bottom_menu.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RecordRepository
) : ViewModel() {
    private val list = mutableListOf<RecordData>()
    private val _recordDataList: MutableLiveData<List<RecordData>> = MutableLiveData()
    val recordDataList: LiveData<List<RecordData>> = _recordDataList

    init {
        _recordDataList.value = list
    }

    fun getLiveDataObserver():
            MutableLiveData<List<RecordData>> = _recordDataList

    fun loadListOfData() {
        repository.loadRecyclerData(_recordDataList)
    }

    fun addData(recordData: RecordData) {
        repository.addRecyclerData(recordData, list, _recordDataList)
    }
}