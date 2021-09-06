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
    private val recordRepository: RecordRepository
) : ViewModel() {

    private val _touchItem = MutableLiveData<Boolean>()
    val touchItem: LiveData<Boolean>
        get() = _touchItem

    private val _searchItem = MutableLiveData<String>()
    val searchItem: LiveData<String>
         get() = _searchItem

    fun touchItem(isTouched: Boolean) {
        _touchItem.value = isTouched
    }

    fun getAllData() = recordRepository.getAllData()

    fun insertPreWorld(searchFragmentEntity: SearchFragmentEntity) {
        recordRepository.insertData(searchFragmentEntity)
        _searchItem.value = searchFragmentEntity.preSearchWord
    }

    fun savePreWorld(searchFragmentEntity: SearchFragmentEntity) {
        _searchItem.value = searchFragmentEntity.preSearchWord
    }

    fun deletePreWorld(searchFragmentEntity: SearchFragmentEntity) {
        recordRepository.deleteData(searchFragmentEntity)
    }

    fun searchDatabase(searchQuery: String) =
        recordRepository.searchDatabase(searchQuery).asLiveData()
}