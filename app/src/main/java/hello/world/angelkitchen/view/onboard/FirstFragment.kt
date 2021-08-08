package hello.world.angelkitchen.view.onboard

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentFirstBinding

class FirstFragment : BindingFragment<FragmentFirstBinding>(R.layout.fragment_first) {
    override fun initView() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.secondFragment)
        }
    }
}