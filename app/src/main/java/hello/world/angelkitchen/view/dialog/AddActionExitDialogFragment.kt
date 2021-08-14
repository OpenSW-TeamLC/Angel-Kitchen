package hello.world.angelkitchen.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingDialogFragment
import hello.world.angelkitchen.databinding.TestOnDialogBinding

@AndroidEntryPoint
class AddActionExitDialogFragment :
    BindingDialogFragment<TestOnDialogBinding>(R.layout.test_on_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.RED))
        setDialogWidthHeight()
        setClickListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setDialogWidthHeight() {
        binding.constraintCancelDialog.layoutParams.width = pixelRatio.screenWidth * 5 / 6
        binding.constraintCancelDialog.layoutParams.height = pixelRatio.screenHeight / 4
    }

    private fun setClickListener() {
        binding.btnCancelDialogCancel.setOnClickListener { dismiss() }
        binding.btnCancelDialogProceed.setOnClickListener {
            dismiss()
            requireActivity().finish()
//            requireActivity().overridePendingTransition(R.anim.none, R.anim.anim_slide_out_bottom)
        }
    }


}
