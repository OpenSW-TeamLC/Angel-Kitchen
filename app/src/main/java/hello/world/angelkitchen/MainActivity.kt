package hello.world.angelkitchen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tv.text = "Angel Kitchen!"
    }
}