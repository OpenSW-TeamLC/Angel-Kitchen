package hello.world.angelkitchen.view.bottom_menu.search

import androidx.lifecycle.MutableLiveData

class RecordRepository {
    private val recordData = ArrayList<RecordData>()

    fun loadRecyclerData(
        recordLiveData: MutableLiveData<ArrayList<RecordData>>
    ) {
        recordData.add(RecordData("무료 급식소"))
        recordData.add(RecordData("송파구 무료"))
        recordData.add(RecordData("밥"))
        recordLiveData.postValue(recordData)
    }

    fun addRecyclerData(
        recordData: ArrayList<RecordData>,
        recordLiveData: MutableLiveData<ArrayList<RecordData>>
    ) {
        this.recordData.add(recordData[0])
        recordLiveData.postValue(this.recordData)
    }
}