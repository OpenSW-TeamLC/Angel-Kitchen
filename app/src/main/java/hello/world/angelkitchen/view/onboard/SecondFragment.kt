package hello.world.angelkitchen.view.onboard

import androidx.navigation.fragment.findNavController
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentSecondBinding

class SecondFragment : BindingFragment<FragmentSecondBinding>(R.layout.fragment_second) {
    override fun initView() {
        binding.btnNext.setOnClickListener {
            findNavController().popBackStack(R.id.step_1_dest, true)
            findNavController().navigate(R.id.step_3_dest)
        }
    }
}