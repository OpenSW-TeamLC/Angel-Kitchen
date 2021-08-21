package hello.world.angelkitchen.view.bottom_menu.search

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recordAdapter: RecordAdapter

    override fun initView() {
        initRecyclerView()

        binding.tfLayout.setStartIconOnClickListener {
            if(binding.tfLayout.startIconContentDescription == "Search Icon") {
                activity?.finish()
            }
        }

        binding.tfEt.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                binding.tfLayout.apply {
                    setStartIconDrawable(R.drawable.ic_back)
                    startIconContentDescription = "Search Icon"
                }
            } else {
                binding.tfLayout.apply {
                    setStartIconDrawable(R.drawable.ic_search)
                    startIconContentDescription = "Back Icon"
                }
            }
        }

        binding.tfEt.setOnKeyListener { _, keyCode, event ->
            if((event.action == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                val inputSearchText = RecordData(binding.tfEt.text.toString())
                viewModel.addRecord(inputSearchText)
                recordAdapter.notifyDataSetChanged()
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }

        viewModel.recordDataList.observe(this, {
            recordAdapter.setData(it)
        })
    }

    override fun onResume() {
        super.onResume()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.tfEt, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    override fun onPause() {
        super.onPause()
        linearLayoutManager = LinearLayoutManager(activity)
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.apply {
            reverseLayout = true
            stackFromEnd = true
        }

        val decoration = DividerItemDecoration(activity, linearLayoutManager.orientation)

        recordAdapter = RecordAdapter(
            emptyList(),
            onClickItem = {
                viewModel.touchItem(it)
                recordAdapter.notifyDataSetChanged()
            },
            onClickDelete = {
                viewModel.deleteRecord(it)
                recordAdapter.notifyDataSetChanged()
            }
        )

        binding.rvRecord.apply {
            layoutManager = linearLayoutManager
            adapter = recordAdapter
            addItemDecoration(decoration)
        }
    }
}