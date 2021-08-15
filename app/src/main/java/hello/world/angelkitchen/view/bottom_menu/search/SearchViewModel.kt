package hello.world.angelkitchen.view.bottom_menu.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RecordRepository
) : ViewModel() {
    private val recordDataList: MutableLiveData<ArrayList<RecordData>> = MutableLiveData()

    fun getLiveDataObserver():
            MutableLiveData<ArrayList<RecordData>> = recordDataList

    fun loadListOfData() {
        repository.loadRecyclerData(recordDataList)
    }

    fun addData(recordData: ArrayList<RecordData>) {
        repository.addRecyclerData(recordData, recordDataList)
    }
}