package com.codelabs.pdam.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.codelabs.pdam.R
import com.codelabs.pdam.databinding.ActivityChooseSertifikatBinding

class ChooseSertifikat : AppCompatActivity() {
    private lateinit var binding : ActivityChooseSertifikatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseSertifikatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setEvent()
    }

    private fun setEvent() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}