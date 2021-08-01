package hello.world.angelkitchen

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tv.text = "Angel Kitchen!"
    }
}