package hello.world.angelkitchen.view.bottom_menu.search.search_result.bottom_sheet

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.fragment.app.activityViewModels
import coil.api.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.github.heyalex.bottomdrawer.BottomDrawer
import com.github.heyalex.bottomdrawer.BottomDrawerDialog
import com.github.heyalex.bottomdrawer.BottomDrawerFragment
import com.github.heyalex.handle.PlainHandleView
import com.github.heyalex.utils.BottomDrawerDelegate
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.databinding.FragmentBottomSheetBinding
import hello.world.angelkitchen.util.extension.setNaverMapRender
import hello.world.angelkitchen.view.bottom_menu.search.search_result.SearchResultViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BottomSheetFragment : BottomDrawerFragment(), OnMapReadyCallback {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val searchResultViewModel: SearchResultViewModel by activityViewModels()
    private val bottomSheetFragmentViewModel: BottomSheetFragmentViewModel by activityViewModels()
    private lateinit var naverMap: NaverMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        searchResultViewModel.searchResultPlace.observe(this, {
            bottomSheetFragmentViewModel.setPlaceInfo(it)
            binding.apply {
                tvTitle.text = bottomSheetFragmentViewModel.resultPlaceInfo.value?.place!!
                tvAddress.text = bottomSheetFragmentViewModel.resultPlaceInfo.value?.address!!
                tvNumber.text = bottomSheetFragmentViewModel.resultPlaceInfo.value?.number!!
                ivThumb.load(bottomSheetFragmentViewModel.resultPlaceInfo.value?.imgPath!!) {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                    transformations(CircleCropTransformation())
                    memoryCachePolicy(CachePolicy.DISABLED)
                }
            }
        })
        setNaverMapRender(R.id.container_map_temp, childFragmentManager, this)

        return binding.root
    }

    override fun configureBottomDrawer(): BottomDrawerDialog {
        return BottomDrawerDialog.build(requireContext()) {
            theme = R.style.Plain
            handleView = PlainHandleView(context).apply {
                val widthHandle =
                    resources.getDimensionPixelSize(R.dimen.bottom_sheet_handle_width)
                val heightHandle =
                    resources.getDimensionPixelSize(R.dimen.bottom_sheet_handle_height)
                val params =
                    FrameLayout.LayoutParams(widthHandle, heightHandle, Gravity.CENTER_HORIZONTAL)

                params.topMargin =
                    resources.getDimensionPixelSize(R.dimen.bottom_sheet_handle_top_margin)

                layoutParams = params
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
    }
}