package com.codelabs.pdam.page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.codelabs.pdam.R
import com.codelabs.pdam.databinding.ActivityChangePasswordBinding
import com.codelabs.pdam.databinding.ActivityChangePasswordSuccessBinding

class ChangePasswordSuccess : AppCompatActivity() {
    private lateinit var binding : ActivityChangePasswordSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
    }

    private fun setEvent() {
        binding.btnFinish.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}