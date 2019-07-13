package com.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startFloatingWidgetService()
    }

    override fun onResume() {
        super.onResume()
        startFloatingWidgetService()
    }

    private fun startFloatingWidgetService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE)
        } else {
            //startService(Intent(this@MainActivity, FloatingService::class.java))
            startService(Intent(this@MainActivity, FloatingServiceA::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK)
                startFloatingWidgetService()
            else
                Toast.makeText(this,resources.getString(R.string.draw_other_app_permission_denied), Toast.LENGTH_SHORT).show()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private val DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222
    }
}
