package hello.world.angelkitchen.view.onboard

import androidx.navigation.fragment.findNavController
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentSecondBinding

class SecondFragment : BindingFragment<FragmentSecondBinding>(R.layout.fragment_second) {
    override fun initView() {
        val animation = binding.lottieAngel
        animation.setAnimation(R.raw.angel)
        animation.playAnimation()

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.step_3_dest)
        }
    }
}