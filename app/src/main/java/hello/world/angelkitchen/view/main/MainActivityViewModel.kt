package hello.world.angelkitchen.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenData
import hello.world.angelkitchen.data.angel_api.AngelScanKitchenPostData
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainActivityRepository: MainActivityRepository
): ViewModel() {

    private val _angelThreeDataList = MutableLiveData<AngelScanKitchenData>()
    val angelThreeDataList: LiveData<AngelScanKitchenData>
        get() = _angelThreeDataList

    fun getThreeData(angelScanKitchenPostData: AngelScanKitchenPostData) {
        mainActivityRepository.makeApi(angelScanKitchenPostData, _angelThreeDataList)
    }

    fun clearDataList() {
        _angelThreeDataList.value?.items?.clear()
    }
}