package hello.world.angelkitchen.view.bottom_menu.direction

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentFindDirectionBinding
import hello.world.angelkitchen.view.bottom_menu.search.search_result.SearchResultViewModel
import hello.world.angelkitchen.view.bottom_menu.search.search_result.bottom_sheet.BottomSheetFragmentViewModel

@AndroidEntryPoint
class DirectionFragment : BindingFragment<FragmentFindDirectionBinding>(R.layout.fragment_find_direction) {
    private val viewModel: DirectionFragmentViewModel by activityViewModels()

    override fun initView() {
        val bundle = arguments
        binding.etArrive.setText(bundle?.getString("share_address")!!)
    }
}