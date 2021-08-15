package hello.world.angelkitchen.view.bottom_menu.search

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by viewModels()
    private val recordAdapter: RecordAdapter by lazy { RecordAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy{ LinearLayoutManager(activity) }

    override fun initView() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        CoroutineScope(Main).launch {
            delay(30)
            imm.showSoftInput(binding.tfEt, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }

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
                val inputSearchTextString = binding.tfEt.text.toString()
                val inputSearchText = ArrayList<RecordData>()
                inputSearchText.add(RecordData(inputSearchTextString))
                addVM(inputSearchText)
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }

        initVM()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        linearLayoutManager.apply {
            reverseLayout = true
            stackFromEnd = true
        }

        binding.rvRecord.apply {
            layoutManager = linearLayoutManager
            adapter = recordAdapter
            setHasFixedSize(true)
        }
    }

    private fun initVM() {
        viewModel.getLiveDataObserver().observe(this, Observer {
            Log.d("SearchFragment", "$it")
            if(it != null) {
                recordAdapter.setRecordData(it)
                recordAdapter.notifyDataSetChanged()
            }
        })
        viewModel.loadListOfData()
    }

    private fun addVM(inputSearchText: ArrayList<RecordData>) {
        viewModel.getLiveDataObserver().observe(this, Observer {
            if(it != null) {
                recordAdapter.setRecordData(it)
                recordAdapter.notifyDataSetChanged()
            }
        })
        viewModel.addData(inputSearchText)
    }
}