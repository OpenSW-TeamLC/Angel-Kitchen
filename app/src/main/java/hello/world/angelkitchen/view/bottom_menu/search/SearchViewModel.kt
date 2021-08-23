package hello.world.angelkitchen.view.bottom_menu.search

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.database.search_fragment.SearchFragmentEntity
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recordRepository: RecordRepository
) : ViewModel() {

    private val _touchItem = MutableLiveData<Boolean>()
    val touchItem: LiveData<Boolean>
        get() = _touchItem

    private val preSearchWord: LiveData<List<SearchFragmentEntity>> =
        recordRepository.getAllData().asLiveData()

    fun touchItem(isTouched: Boolean) {
        _touchItem.value = isTouched
    }

    fun getAllData() = preSearchWord.asFlow()

    fun insertPreWorld(searchFragmentEntity: SearchFragmentEntity) {
        recordRepository.insertData(searchFragmentEntity)
    }

    fun deletePreWorld(searchFragmentEntity: SearchFragmentEntity) {
        recordRepository.deleteData(searchFragmentEntity)
    }
}