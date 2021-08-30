package hello.world.angelkitchen.view.bottom_menu.direction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hello.world.angelkitchen.data.reverse_geo_api.Land
import hello.world.angelkitchen.data.reverse_geo_api.Result1
import javax.inject.Inject

@HiltViewModel
class DirectionFragmentViewModel @Inject constructor(
    private val directionFragmentRepository: DirectionFragmentRepository
) : ViewModel() {

    private val _curLocation = MutableLiveData<Result1>()
    val curLocation: LiveData<Result1>
        get() = _curLocation

    fun getGeoApi(
        apiKeyId: String,
        apiKey: String,
        coords: String,
    ) {
        directionFragmentRepository.makeApiCall(apiKeyId, apiKey, coords, _curLocation)
    }
}