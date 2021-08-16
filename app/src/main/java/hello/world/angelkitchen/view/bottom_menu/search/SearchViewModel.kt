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

    val recordDataList = MutableLiveData<List<RecordData>>()
    private val data = arrayListOf<RecordData>()


    fun toggleRecord(recordData: RecordData) {
        recordData.isClicked = !recordData.isClicked
        recordDataList.value = data
    }

    fun addRecord(recordData: RecordData) {
        data.add(recordData)
        recordDataList.value = data
    }

    fun deleteRecord(recordData: RecordData) {
        data.remove(recordData)
        recordDataList.value = data
    }
}