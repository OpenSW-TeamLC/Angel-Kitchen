package hello.world.angelkitchen.view.bottom_menu.search

import androidx.lifecycle.MutableLiveData

class RecordRepository {

    fun loadRecyclerData(
        recordLiveData: MutableLiveData<List<RecordData>>
    ) {
//        recordData.add(RecordData("무료 급식소"))
//        recordData.add(RecordData("송파구 무료"))
//        recordData.add(RecordData("밥"))
//        this.recordData.add(recordData[0])
//        this.recordData.add(recordData[1])
//        this.recordData.add(recordData[2])
//        recordLiveData.postValue(recordData)
    }

    fun addRecyclerData(
        recordData: RecordData,
        recordDataList: MutableList<RecordData>,
        recordLiveData: MutableLiveData<List<RecordData>>
    ) {
        recordDataList.add(recordData)
        recordLiveData.value = recordDataList
    }
}