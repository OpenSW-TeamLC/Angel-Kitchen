package hello.world.angelkitchen.view.bottom_menu.search.search_result.bottom_sheet

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import coil.api.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.github.heyalex.bottomdrawer.BottomDrawerDialog
import com.github.heyalex.bottomdrawer.BottomDrawerFragment
import com.github.heyalex.handle.PlainHandleView
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.databinding.FragmentBottomSheetBinding
import hello.world.angelkitchen.view.bottom_menu.search.search_result.SearchResultViewModel

@AndroidEntryPoint
class BottomSheetFragment : BottomDrawerFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val searchResultViewModel: SearchResultViewModel by activityViewModels()
    private val bottomSheetFragmentViewModel: BottomSheetFragmentViewModel by activityViewModels()

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
}