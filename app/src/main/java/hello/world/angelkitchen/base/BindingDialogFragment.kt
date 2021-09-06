package hello.world.angelkitchen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import hello.world.angelkitchen.util.PixelRatio
import javax.inject.Inject

abstract class BindingDialogFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : DialogFragment() {
    @Inject
    lateinit var pixelRatio: PixelRatio

    private var _binding: T? = null
    protected val binding: T
        get() = requireNotNull(_binding) { "Wrong Initialization" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}