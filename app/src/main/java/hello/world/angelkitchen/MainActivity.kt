package hello.world.angelkitchen

import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.databinding.ActivityMainBinding
import hello.world.angelkitchen.view.dialog.AddActionExitDialogFragment
import hello.world.angelkitchen.view.onboard.OnboardActivity

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initView() {
        AddActionExitDialogFragment().show(supportFragmentManager, "Test")

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

    override fun startView() {

    }

    private fun startFragment() {
        val intent = Intent(this@MainActivity, OnboardActivity::class.java)
        startActivity(intent)
    }

    override fun stopView() {
        finish()
    }
}