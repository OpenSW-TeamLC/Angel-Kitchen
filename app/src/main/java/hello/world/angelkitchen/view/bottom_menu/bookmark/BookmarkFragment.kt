package hello.world.angelkitchen.view.bottom_menu.bookmark

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentBookmarkBinding

@AndroidEntryPoint
class BookmarkFragment : BindingFragment<FragmentBookmarkBinding>(R.layout.fragment_bookmark) {
    private val viewModel: BookmarkViewModel by activityViewModels()
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }
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
            },
            onClickButton = {
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
            }
        }).apply {
            attachToRecyclerView(binding.rvBookmark)
        }
    }
}