package hello.world.angelkitchen.view.bottom_menu.bookmark

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentBookmarkBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
    }
}