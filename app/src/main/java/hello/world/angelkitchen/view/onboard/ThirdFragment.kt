package hello.world.angelkitchen.view.onboard

import android.content.Intent
import androidx.navigation.fragment.findNavController
import hello.world.angelkitchen.MainActivity
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentThirdBinding

class ThirdFragment : BindingFragment<FragmentThirdBinding>(R.layout.fragment_third) {
    override fun initView() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}