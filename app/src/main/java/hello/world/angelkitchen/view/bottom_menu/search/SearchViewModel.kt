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

    val recordDataList = arrayListOf<RecordData>()


    fun toggleRecord(recordData: RecordData) {
        recordData.isClicked = !recordData.isClicked
    }

    fun addRecord(recordData: RecordData) {
        recordDataList.add(recordData)
    }

    fun deleteRecord(recordData: RecordData) {
        recordDataList.remove(recordData)
    }
}