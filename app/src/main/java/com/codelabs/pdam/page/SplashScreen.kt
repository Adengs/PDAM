package com.codelabs.pdam.page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.codelabs.pdam.R
import com.codelabs.pdam.helper.SharedPreference

class SplashScreen : AppCompatActivity() {
    lateinit var sph : SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
    }

    override fun onStart() {
        sph = SharedPreference(this)
        super.onStart()
        if (sph.getBoolean()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{
            Handler().postDelayed({
                startActivity(Intent(this, Login::class.java))
                finish()
            }, 3000)
        }
    }
}