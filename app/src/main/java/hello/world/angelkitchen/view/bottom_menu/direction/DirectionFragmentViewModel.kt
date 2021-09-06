package hello.world.angelkitchen.view.bottom_menu.direction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.data.reverse_geo_api.Addresse
import hello.world.angelkitchen.data.reverse_geo_api.Result1
import hello.world.angelkitchen.data.reverse_geo_api.Result_path
import javax.inject.Inject

@HiltViewModel
class DirectionFragmentViewModel @Inject constructor(
    private val directionFragmentRepository: DirectionFragmentRepository
) : ViewModel() {

    private val _goalLocation = MutableLiveData<Addresse>()
    val goalLocation: LiveData<Addresse>
        get() = _goalLocation

    private val _curLocation = MutableLiveData<Result1>()
    val curLocation: LiveData<Result1>
        get() = _curLocation

    private val _getResultPath = MutableLiveData<List<Result_path>>()
    val getResultPath: LiveData<List<Result_path>>
        get() = _getResultPath

    fun getGeoApi(
        apiKeyId: String,
        apiKey: String,
        query: String,
    ) {
        directionFragmentRepository.makeGeoApiCall(apiKeyId, apiKey, query, _goalLocation)
    }

    fun getReverseGeoApi(
        apiKeyId: String,
        apiKey: String,
        coords: String,
    ) {
        directionFragmentRepository.makeReverseGeoApiCall(apiKeyId, apiKey, coords, _curLocation)
    }

    fun getResultPath(
        apiKeyId: String,
        apiKey: String,
        start: String,
        goal: String
    ) {
        directionFragmentRepository.makeGetResultPathApiCall(apiKeyId, apiKey, start, goal, _getResultPath)
    }
}