package hello.world.angelkitchen.view.bottom_menu.direction

import android.os.Bundle
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.databinding.ActivityDirectionAttachBinding
import hello.world.angelkitchen.util.extension.replace

@AndroidEntryPoint
class DirectionAttachActivity : BindingActivity<ActivityDirectionAttachBinding>(R.layout.activity_direction_attach) {
    private val directionFragment: DirectionFragment by lazy { DirectionFragment() }

    override fun initView() {
        val intent = intent.getStringExtra("share_address")
        val bundle = Bundle(1)
        bundle.putString("share_address", intent)
        directionFragment.arguments = bundle
        replace(R.id.container_direction, directionFragment)
    }

    override fun startView() {

    }

    override fun stopView() {

    }
}