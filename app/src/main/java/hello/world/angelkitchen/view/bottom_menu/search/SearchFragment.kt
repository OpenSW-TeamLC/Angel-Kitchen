package hello.world.angelkitchen.view.bottom_menu.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.database.search_fragment.SearchFragmentEntity
import hello.world.angelkitchen.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recordAdapter: RecordAdapter

    override fun initView() {
        initRecyclerView()
        binding.tfEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchDatabase(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.tfLayout.setStartIconOnClickListener {
            if (binding.tfLayout.startIconContentDescription == "Search Icon") {
                activity?.finish()
            }
        }

        binding.tfEt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
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
            if ((event.action == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                val inputSearchText = SearchFragmentEntity(binding.tfEt.text.toString())
                viewModel.insertPreWorld(inputSearchText)
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }

        CoroutineScope(Main).launch {
            viewModel.getAllData().collect { it ->
                recordAdapter.setData(it.distinctBy {
                    it.preSearchWord
                })
            }
        }

//        binding.chip1.setOnClickListener {
//            viewModel.getAllAngelData().observe(this, {
//                if(it != null) Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
//                else Toast.makeText(activity, "Null", Toast.LENGTH_LONG).show()
//            })
//        }
//        viewModel.loadAllAngelData()
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

        val decoration = DividerItemDecoration(activity, linearLayoutManager.orientation)

        recordAdapter = RecordAdapter(
            emptyList(),
            onClickItem = {
                viewModel.touchItem(true)
            },
            onClickDelete = {
                viewModel.deletePreWorld(it)
            }
        )

        binding.rvRecord.apply {
            layoutManager = linearLayoutManager
            adapter = recordAdapter
            addItemDecoration(decoration)
        }
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let { it ->
                recordAdapter.setData(it.distinctBy {
                    it.preSearchWord
                })
            }
        })
    }
}