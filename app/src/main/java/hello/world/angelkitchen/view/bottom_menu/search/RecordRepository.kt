package hello.world.angelkitchen.view.bottom_menu.search

import androidx.lifecycle.MutableLiveData

class RecordRepository {

    // 나중에 Room 이랑 연동할 때 사용
    fun loadRecyclerData(
        recordLiveData: MutableLiveData<List<RecordData>>
    ) {
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