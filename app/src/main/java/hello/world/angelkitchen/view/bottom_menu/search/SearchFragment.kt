package hello.world.angelkitchen.view.bottom_menu.search

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
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
    }
}