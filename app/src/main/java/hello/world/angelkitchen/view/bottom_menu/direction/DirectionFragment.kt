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
        viewModel.getGeoApi("uzlzuhd2pa", "INnDxBgwB6Tt20sjSdFEqi6smxIBUNp4r7EkDUBc", "127.1058342,37.359708")

        val bundle = arguments
        if (bundle != null) {
            binding.etArrive.setText(bundle.getString("share_address"))
        }

        viewModel.curLocation.observe(this, {
            val location = it.region.area1.name + it.region.area2.name + it.region.area3.name + it.region.area4.name
            binding.etStart.setText(location)
        })
    }
}