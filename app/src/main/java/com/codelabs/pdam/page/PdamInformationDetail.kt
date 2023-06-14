package com.codelabs.pdam.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.codelabs.pdam.R
import com.codelabs.pdam.databinding.ActivityPdamInformationDetailBinding

class PdamInformationDetail : AppCompatActivity() {
    private lateinit var binding : ActivityPdamInformationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdamInformationDetailBinding.inflate(layoutInflater)
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