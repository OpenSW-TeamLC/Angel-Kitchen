package hello.world.angelkitchen.view.bottom_menu.search.search_result.bottom_sheet

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.UiThread
import androidx.fragment.app.activityViewModels
import coil.api.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.github.heyalex.bottomdrawer.BottomDrawerDialog
import com.github.heyalex.bottomdrawer.BottomDrawerFragment
import com.github.heyalex.handle.PlainHandleView
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.database.bookmark_fragment.BookmarkFragmentEntity
import hello.world.angelkitchen.databinding.FragmentBottomSheetBinding
import hello.world.angelkitchen.util.extension.add
import hello.world.angelkitchen.util.extension.setNaverMapRender
import hello.world.angelkitchen.view.bottom_menu.direction.DirectionAttachActivity
import hello.world.angelkitchen.view.bottom_menu.search.search_result.SearchResultViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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
                tvTarget.text = bottomSheetFragmentViewModel.resultPlaceInfo.value?.target!!
                tvTime.text = bottomSheetFragmentViewModel.resultPlaceInfo.value?.startTime!!
                tvDay.text = bottomSheetFragmentViewModel.resultPlaceInfo.value?.day!!
                tvStart.text = bottomSheetFragmentViewModel.resultPlaceInfo.value?.start!!
                ivThumb.load(bottomSheetFragmentViewModel.resultPlaceInfo.value?.imgPath!!) {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                    transformations(CircleCropTransformation())
                    memoryCachePolicy(CachePolicy.DISABLED)
                }

            }
        })
        setNaverMapRender(R.id.container_map_temp, childFragmentManager, this)

        binding.ivBookmark.setOnClickListener {
            when (it.tag) {
                "none" -> {
                    searchResultViewModel.searchResultPlace.value!!.like = true
                    bottomSheetFragmentViewModel.insertBookmark(searchResultViewModel.searchResultPlace.value!!)
                    binding.ivBookmark.setImageResource(R.drawable.ic_star_fill)
                    binding.ivBookmark.tag = "set"
                    Snackbar.make(binding.mainContainer, "저장되었습니다", LENGTH_SHORT).show()
                }
                "set" -> {
                    searchResultViewModel.searchResultPlace.value!!.like = false
                    bottomSheetFragmentViewModel.deleteByNumber(searchResultViewModel.searchResultPlace.value!!.number)
                    binding.ivBookmark.setImageResource(R.drawable.ic_star)
                    binding.ivBookmark.tag = "none"
                    Snackbar.make(binding.mainContainer, "삭제되었습니다.", LENGTH_SHORT).show()
                }
            }
        }

        binding.btnFind.setOnClickListener {
            val intent = Intent(activity, DirectionAttachActivity::class.java)
            intent.putExtra("share_address", binding.tvAddress.text.toString())
            startActivity(intent)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        bottomSheetFragmentViewModel.getAllLikeData().observe(this, { it ->
            it.iterator().forEach {
                if (it.place == binding.tvTitle.text.toString()) {
                    binding.ivBookmark.setImageResource(R.drawable.ic_star_fill)
                    binding.ivBookmark.tag = "set"
                }
            }
        })
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
                    FrameLayout.LayoutParams(
                        widthHandle,
                        heightHandle,
                        Gravity.CENTER_HORIZONTAL
                    )
                params.topMargin =
                    resources.getDimensionPixelSize(R.dimen.bottom_sheet_handle_top_margin)

                layoutParams = params
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dialog.cancel()
        dialog.dismiss()
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