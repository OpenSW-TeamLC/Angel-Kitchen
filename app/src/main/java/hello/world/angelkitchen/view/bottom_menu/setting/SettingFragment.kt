package hello.world.angelkitchen.view.bottom_menu.setting

import com.colinrtwhite.licensesdialog.LicensesDialog
import com.colinrtwhite.licensesdialog.license.ApacheLicense20
import com.colinrtwhite.licensesdialog.license.MitLicense
import com.colinrtwhite.licensesdialog.model.Copyright
import com.colinrtwhite.licensesdialog.model.Notice
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingFragment
import hello.world.angelkitchen.databinding.FragmentSettingBinding

@AndroidEntryPoint
class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    override fun initView() {
        val notices = listOf(
            Notice("Logger", ApacheLicense20, "https://github.com/JakeWharton/timber", Copyright("Wharton", 2013)),
            Notice("android-stepper", ApacheLicense20, "https://github.com/acefalobi/android-stepper", Copyright("Ayomide Falobi", 2020)),
            Notice("TedPermission", ApacheLicense20, "https://github.com/ParkSangGwon/TedPermission", Copyright("Ted Park", 2021)),
            Notice("android-lottie", ApacheLicense20, "https://github.com/airbnb/lottie-android", Copyright("airbnb", 2004)),
            Notice("coil", ApacheLicense20, "https://github.com/coil-kt/coil", Copyright("Coil Contributors", 2021)),
            Notice("BottomDrawer", MitLicense, "https://github.com/HeyAlex/BottomDrawer", Copyright("Alex Fialko", 2019)),
            Notice("Retrofit2", ApacheLicense20, "https://github.com/square/retrofit", Copyright("square", 2004)),
            Notice("okhttp3", ApacheLicense20, "https://github.com/square/okhttp", Copyright("square", 2004)),
        )
        binding.containerLicense.setOnClickListener {
            LicensesDialog.Builder(requireContext())
                .setNotices(notices)
                .show()
        }
    }
}