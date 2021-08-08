package hello.world.angelkitchen.view.onboard

import androidx.navigation.findNavController
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.databinding.ActivityOnboardBinding

class OnboardActivity : BindingActivity<ActivityOnboardBinding>(R.layout.activity_onboard) {
    override fun initView() {
        val stepper = binding.stepper
        stepper.setupWithNavController(findNavController(R.id.fg_onboard))
    }

    override fun stopView() {

    }
}