package hello.world.angelkitchen.view.bottom_menu.search

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.database.search_fragment.SearchFragmentEntity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    private val angelAllDataRepository: AngelAllDataRepository
) : ViewModel() {

    private val _touchItem = MutableLiveData<Boolean>()
    val touchItem: LiveData<Boolean>
        get() = _touchItem

    //    언젠간 쓸 날이...?
//    val preSearchWord: LiveData<List<SearchFragmentEntity>> =
//        recordRepository.getAllData().asLiveData()

    private val _angelAllData = MutableLiveData<String>()
    val angelAllData: LiveData<String>
        get() = _angelAllData

    fun touchItem(isTouched: Boolean) {
        _touchItem.value = isTouched
    }

    fun getAllData() = recordRepository.getAllData()

    fun insertPreWorld(searchFragmentEntity: SearchFragmentEntity) {
        recordRepository.insertData(searchFragmentEntity)
    }

    fun deletePreWorld(searchFragmentEntity: SearchFragmentEntity) {
        recordRepository.deleteData(searchFragmentEntity)
    }

    fun searchDatabase(searchQuery: String) =
        recordRepository.searchDatabase(searchQuery).asLiveData()

//    fun getAllAngelData(): LiveData<String> = angelAllData
//
//    fun loadAllAngelData() {
//        angelAllDataRepository.makeApiCall(_angelAllData)
//    }
}