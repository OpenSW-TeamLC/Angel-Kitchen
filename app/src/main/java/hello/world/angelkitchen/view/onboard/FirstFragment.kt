package hello.world.angelkitchen.view.onboard

import android.content.Intent
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.AngelKitchenApplication
import hello.world.angelkitchen.view.main.MainActivity
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentFirstBinding

@AndroidEntryPoint
class FirstFragment : BindingFragment<FragmentFirstBinding>(R.layout.fragment_first) {
    override fun initView() {
        checkPreference()
    }

    private fun checkPreference() {
        val isSaved = AngelKitchenApplication.prefs.getString("onboard_save", "isSaved")
        if(isSaved == "save") {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        else startOnboard()
    }

    private fun startOnboard() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }
}