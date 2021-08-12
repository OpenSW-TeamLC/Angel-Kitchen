package hello.world.angelkitchen.view.onboard

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import hello.world.angelkitchen.R
import hello.world.angelkitchen.base.BindingActivity
import hello.world.angelkitchen.databinding.ActivityOnboardBinding

@AndroidEntryPoint
class OnboardActivity : BindingActivity<ActivityOnboardBinding>(R.layout.activity_onboard) {
    private lateinit var context: Context
    private lateinit var permissionlistener: PermissionListener

    override fun initView() {
        permissionlistener = object : PermissionListener {
            // Access permission
            override fun onPermissionGranted() {

            }

            // Deny permission
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(
                    this@OnboardActivity,
                    "권한 허용을 하지 않으면 서비스를 이용할 수 없습니다.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
        context = this@OnboardActivity
        checkPermission()
    }

    override fun startView() {
        initStepController()
    }

    private fun checkPermission() {
//        if (Build.VERSION.SDK_INT >= 26) { // 출처를 알 수 없는 앱 설정 화면 띄우기
//            val pm: PackageManager = context.packageManager
//            Log.e("Package Name", packageName)
//            if (!pm.canRequestPackageInstalls()) {
//                startActivity(
//                    Intent(
//                        Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
//                        Uri.parse("package:$packageName")
//                    )
//                )
//            }
//        }

        TedPermission.with(context)
            .setPermissionListener(permissionlistener)
            .setRationaleMessage("앱을 이용하기 위해서는 접근 권한이 필요합니다")
            .setDeniedMessage("앱에서 요구하는 권한설정이 필요합니다...\n [설정] > [권한] 에서 사용으로 활성화해주세요.")
            .setPermissions(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CALL_LOG,  // 안드로이드 9.0 에서는 이것도 추가하라고 되어 있음.
                Manifest.permission.CALL_PHONE,  // 전화걸기 및 관리
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).check()
    }

    private fun initStepController() {
        val stepper = binding.stepper
        stepper.setupWithNavController(findNavController(R.id.fg_onboard))
    }

    override fun stopView() {

    }
}