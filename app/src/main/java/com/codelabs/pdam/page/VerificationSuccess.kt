package com.codelabs.pdam.page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.codelabs.pdam.R
import com.codelabs.pdam.databinding.ActivityVerificationSuccessBinding

class VerificationSuccess : AppCompatActivity() {
    private lateinit var binding : ActivityVerificationSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
    }

    private fun setEvent() {
        binding.btnFinish.setOnClickListener {
            onBackPressed()
//            startActivity(Intent(this, Login::class.java))
//            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        startActivity(Intent(this, Login::class.java))
//        finishAndRemoveTask()
    }
}