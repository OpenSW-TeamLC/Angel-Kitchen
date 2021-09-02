package hello.world.angelkitchen.view.bottom_menu.search.search_result

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.database.bookmark_fragment.BookmarkFragmentEntity
import hello.world.angelkitchen.databinding.FragmentSearchResultBinding
import hello.world.angelkitchen.util.extension.setNaverMapRender
import hello.world.angelkitchen.view.bottom_menu.search.SearchViewModel
import hello.world.angelkitchen.view.bottom_menu.search.search_result.bottom_sheet.BottomSheetFragment

@AndroidEntryPoint
class SearchResultFragment :
    BindingFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result),
    OnMapReadyCallback {

    private val searchResultViewModel: SearchResultViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()
    private lateinit var searchResultAdapter: SearchResultAdapter
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }
    private val sheet: BottomSheetFragment by lazy { BottomSheetFragment() }
    private lateinit var naverMap: NaverMap

    override fun initView() {

        setNaverMapRender(R.id.container_map, activity?.supportFragmentManager!!, this)

        searchResultViewModel.resultList.observe(this, {
            searchResultAdapter.setData(it)
        })

        searchResultViewModel.searchResultPlace.observe(this, {
        })

        initRecyclerView()

        // 추후 BindingAdapter로 변경
        binding.ivPre.setBackgroundDrawable(ColorDrawable(Color.RED))
        binding.ivNext.setBackgroundDrawable(ColorDrawable(Color.BLUE))

        binding.recycler.setItemSelectListener(object : CarouselLayoutManager.OnSelected {
            override fun onItemSelected(position: Int) {
                val allSize = searchResultAdapter.itemCount
                when (position) {
                    allSize - 1 -> {
                        binding.ivNext.setBackgroundDrawable(ColorDrawable(Color.RED))
                        binding.ivPre.setBackgroundDrawable(ColorDrawable(Color.BLUE))
                    }
                    0 -> {
                        binding.ivPre.setBackgroundDrawable(ColorDrawable(Color.RED))
                        binding.ivNext.setBackgroundDrawable(ColorDrawable(Color.BLUE))
                    }
                    else -> {
                        binding.ivPre.setBackgroundDrawable(ColorDrawable(Color.BLUE))
                        binding.ivNext.setBackgroundDrawable(ColorDrawable(Color.BLUE))
                    }
                }
            }
        })

        searchViewModel.searchItem.observe(this, {
            searchResultViewModel.getSearchData(it)
        })

        searchResultViewModel.getSearchData.observe(this, {
            for(i in it.items.indices) {
                searchResultViewModel.addPlace(
                    BookmarkFragmentEntity(
                        "https://picsum.photos/200/300",
                        it.items[i].fcltyNm,
                        it.items[i].lnmadr,
                        it.items[i].phoneNumber,
                        false
                    )
                )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchResultViewModel.removeAllSearchResult()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
    }

    private fun initRecyclerView() {
        linearLayoutManager.apply {
            reverseLayout = true
            stackFromEnd = true
        }

        searchResultAdapter = SearchResultAdapter(
            emptyList(),
            onClickItem = {
                searchResultViewModel.touchItem(it)
                sheet.show(activity?.supportFragmentManager!!, "DemoBottomSheetFragment")
            })

        binding.recycler.adapter = searchResultAdapter
        binding.recycler.setFlat(true)
        binding.recycler.setAlpha(true)
    }
}