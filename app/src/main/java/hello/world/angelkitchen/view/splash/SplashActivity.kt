package hello.world.angelkitchen.view.splash

import android.content.Intent
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.databinding.ActivitySplashBinding
import hello.world.angelkitchen.view.onboard.OnboardActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun initView() {
        CoroutineScope(Main).launch {
            delay(4000)
            startActivity(Intent(this@SplashActivity, OnboardActivity::class.java))
            finish()
        }
    }

    override fun startView() {

    }

    override fun stopView() {

    }
}