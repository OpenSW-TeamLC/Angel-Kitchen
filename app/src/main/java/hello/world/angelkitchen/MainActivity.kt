package hello.world.angelkitchen

import android.content.Intent
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.databinding.ActivityMainBinding
import hello.world.angelkitchen.view.onboard.OnboardActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initView() {
        binding.btnFirst.setOnClickListener {
            startFragment()
        }
        binding.btnSecond.setOnClickListener {
            startFragment()
        }
        binding.btnThird.setOnClickListener {
            startFragment()
        }
    }

    private fun startFragment() {
        val intent = Intent(this@MainActivity, OnboardActivity::class.java)
        startActivity(intent)
    }

    override fun stopView() {
        finish()
    }
}