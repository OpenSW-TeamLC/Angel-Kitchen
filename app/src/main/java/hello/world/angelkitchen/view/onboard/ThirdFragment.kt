package hello.world.angelkitchen.view.onboard

import android.content.Intent
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.AngelKitchenDevelopApplication
import hello.world.angelkitchen.view.main.MainActivity
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentThirdBinding

@AndroidEntryPoint
class ThirdFragment : BindingFragment<FragmentThirdBinding>(R.layout.fragment_third) {
    override fun initView() {
        binding.btnNext.setOnClickListener {
            AngelKitchenDevelopApplication.prefs.setString("onboard_save", "save")

            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.btnPre.setOnClickListener {
            findNavController().navigate(R.id.action_step_3_dest_to_step_2_dest)
        }
    }
}