package com.codelabs.pdam.page

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.codelabs.pdam.R
import com.codelabs.pdam.databinding.ActivityMainBinding
import com.codelabs.pdam.fragment.ProfileFragment
import com.codelabs.pdam.fragment.HistoryFragment
import com.codelabs.pdam.fragment.HomeFragment
import com.codelabs.pdam.fragment.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    var count = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        window.statusBarColor = Color.TRANSPARENT
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION

        setContentView(binding.root)
        supportActionBar?.hide()

        binding.bottomNavigate.setOnItemSelectedListener { i ->
            var selected : Fragment = HomeFragment()
            when (i.itemId){
                R.id.home -> {
                    selected = HomeFragment()
//                    window.statusBarColor = resources.getColor(R.color.blue_pdam)
                    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                }
                R.id.history -> {
                    selected = HistoryFragment()
//                    window.statusBarColor = resources.getColor(R.color.white)
                    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
                }
                R.id.notification -> {
                    selected = NotificationFragment()
//                    window.statusBarColor = resources.getColor(R.color.white)
                    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
                }
                R.id.account -> {
                    selected = ProfileFragment()
//                    window.statusBarColor = resources.getColor(R.color.blue_pdam)
                    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                }
            }
            var fr = supportFragmentManager.beginTransaction()
            fr.replace(R.id.fl_fragment, selected)
            fr.commit()
            true
        }

        binding.bottomNavigate.selectedItemId = R.id.home

        supportFragmentManager.addOnBackStackChangedListener {
            for (i in 0 until supportFragmentManager.backStackEntryCount){
                Log.d("MainActivity", supportFragmentManager.getBackStackEntryAt(i).name.toString())
            }
        }
    }

//    private val onBottomListener = BottomNavigationView.OnNavigationItemSelectedListener { i ->
//        var selected : Fragment = HomeFragment()
//        when (i.itemId){
//            R.id.home -> {
//                selected = HomeFragment()
//            }
//            R.id.history -> {
//                selected = HistoryFragment()
//            }
//            R.id.notification -> {
//                selected = NotificationFragment()
//            }
//            R.id.account -> {
//                selected = AccountFragment()
//            }
//        }
//        var fr = supportFragmentManager.beginTransaction()
//        fr.replace(R.id.fl_fragment, selected)
//        fr.addToBackStack("fr - ${count++}")
//        fr.commit()
//        true
//    }

    override fun onBackPressed() {
        if (binding.bottomNavigate.selectedItemId == R.id.home){
            super.onBackPressed()
        }else{
            binding.bottomNavigate.selectedItemId = R.id.home
        }
    }
}