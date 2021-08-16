package hello.world.angelkitchen.view.bottom_menu.search

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.ActivityMainBinding
import hello.world.angelkitchen.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by activityViewModels()
    private val linearLayoutManager: LinearLayoutManager by lazy{ LinearLayoutManager(activity) }
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
                binding.rvRecord.adapter?.notifyDataSetChanged()
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }

//        viewModel.test.observe(this, {
//            binding.tfEt.setText(it)
//        })
    }

    override fun onResume() {
        super.onResume()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.tfEt, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun initRecyclerView() {
        linearLayoutManager.apply {
            reverseLayout = true
            stackFromEnd = true
        }

        recordAdapter = RecordAdapter(
            viewModel.recordDataList,
            onClickItem = {
                viewModel.toggleRecord(it)
                binding.rvRecord.adapter?.notifyDataSetChanged()
            },
            onClickDelete = {
                viewModel.deleteRecord(it)
                binding.rvRecord.adapter?.notifyDataSetChanged()
            }
        )

        binding.rvRecord.apply {
            layoutManager = linearLayoutManager
            adapter = recordAdapter
        }
    }
}