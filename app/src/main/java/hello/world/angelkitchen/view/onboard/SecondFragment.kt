package hello.world.angelkitchen.view.onboard

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentSecondBinding

@AndroidEntryPoint
class SecondFragment : BindingFragment<FragmentSecondBinding>(R.layout.fragment_second) {
    override fun initView() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_thirdFragment)
//            val animation = binding.lottieAngel
//            animation.setAnimation(R.raw.angel)
//            animation.playAnimation()
//            animation.
        }
    }
}