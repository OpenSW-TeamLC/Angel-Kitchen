package hello.world.angelkitchen.view.bottom_menu.search.search_result

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentSearchResultBinding
import hello.world.angelkitchen.view.bottom_menu.search.SearchViewModel

@AndroidEntryPoint
class SearchResultFragment : BindingFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val searchResultViewModel :SearchResultViewModel by activityViewModels()
    private lateinit var searchResultAdapter: SearchResultAdapter
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }

    override fun initView() {
        Toast.makeText(activity, searchViewModel.sharePlace.value, Toast.LENGTH_SHORT).show()

        searchResultViewModel.resultList.observe(this, {
            searchResultAdapter.setData(it)
        })

        for(i in 1..3)
        searchResultViewModel.addPlace(
            SearchResultData(
                "https://picsum.photos/200/300",
                "${i}강남 급식소",
                "서울 강남구 테헤란로 13-1",
                "02-1234-5678"
            )
        )
        initRecyclerView()

        Toast.makeText(activity, "${binding.recycler.getSelectedPosition()}", Toast.LENGTH_SHORT).show()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchResultViewModel.removeAllSearchResult()
    }

    private fun initRecyclerView() {
        linearLayoutManager.apply {
            reverseLayout = true
            stackFromEnd = true
        }

        searchResultAdapter = SearchResultAdapter(emptyList())

        binding.recycler.adapter = searchResultAdapter
        binding.recycler.setFlat(true)
        binding.recycler.setAlpha(true)
    }
}