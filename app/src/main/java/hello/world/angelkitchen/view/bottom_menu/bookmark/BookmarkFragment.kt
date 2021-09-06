package hello.world.angelkitchen.view.bottom_menu.bookmark

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentBookmarkBinding
import hello.world.angelkitchen.view.bottom_menu.direction.DirectionAttachActivity
import hello.world.angelkitchen.view.bottom_menu.search.search_result.SearchResultViewModel
import hello.world.angelkitchen.view.bottom_menu.search.search_result.bottom_sheet.BottomSheetFragment

@AndroidEntryPoint
class BookmarkFragment : BindingFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {
    private val viewModel: BookmarkViewModel by activityViewModels()
    private val searchResultViewModel: SearchResultViewModel by activityViewModels()
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }
    private val sheet: BottomSheetFragment by lazy { BottomSheetFragment() }
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun initView() {
        initRecyclerView()

        viewModel.getAllData().observe(this, {
            bookmarkAdapter.setData(it)
        })
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
                searchResultViewModel.touchItem(it)
                sheet.show(activity?.supportFragmentManager!!, "BookmarkFragment")
            },
            onClickButton = {
                val intent = Intent(activity, DirectionAttachActivity::class.java)
                intent.putExtra("share_address", it.address)
                startActivity(intent)
            }
        )

        binding.rvBookmark.apply {
            layoutManager = linearLayoutManager
            adapter = bookmarkAdapter
            addItemDecoration(decoration)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteBookmark(viewModel.getAllData().value!![viewHolder.adapterPosition].number)
                Snackbar.make(binding.container, "삭제되었습니다.", LENGTH_SHORT).show()
            }
        }).apply {
            attachToRecyclerView(binding.rvBookmark)
        }
    }
}