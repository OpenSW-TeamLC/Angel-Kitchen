package hello.world.angelkitchen.view.bottom_menu.bookmark

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentBookmarkBinding
import hello.world.angelkitchen.view.bottom_menu.search.RecordAdapter
import hello.world.angelkitchen.view.bottom_menu.search.RecordData

@AndroidEntryPoint
class BookmarkFragment : BindingFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {
    private val viewModel: BookmarkViewModel by activityViewModels()
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun initView() {

        viewModel.bookmarkDataList.observe(this, {
            bookmarkAdapter.setData(it)
        })

        viewModel.addBookmark(
            BookmarkData(
                "https://picsum.photos/200/300",
                "강남 급식소",
                "서울 강남구 테헤란로 13-1",
                "02-1234-5678"
            )
        )
        viewModel.addBookmark(
            BookmarkData(
                "https://picsum.photos/200/300",
                "강북 급식소",
                "서울 강남구 테헤란로 13-1",
                "02-1234-5678"
            )
        )
        viewModel.addBookmark(
            BookmarkData(
                "https://picsum.photos/200/300",
                "강서 급식소",
                "서울 강남구 테헤란로 13-1",
                "02-1234-5678"
            )
        )

        initRecyclerView()
    }

    private fun initRecyclerView() {
        linearLayoutManager.apply {
            reverseLayout = true
            stackFromEnd = true
        }
        val decoration = DividerItemDecoration(activity, linearLayoutManager.orientation)

        bookmarkAdapter = BookmarkAdapter(
            emptyList(),
            onClickItem = {
                val position = viewModel.showToastBookmark(it)
                Toast.makeText(activity, "$position List Clicked", Toast.LENGTH_SHORT).show()
            },
            onClickButton = {
                Toast.makeText(activity, "Button Clicked", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvBookmark.apply {
            layoutManager = linearLayoutManager
            adapter = bookmarkAdapter
            addItemDecoration(decoration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.removeAllBookmark()
        initView()
    }
}