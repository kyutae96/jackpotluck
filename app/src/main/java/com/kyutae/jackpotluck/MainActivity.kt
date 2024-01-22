package com.kyutae.jackpotluck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kyutae.jackpotluck.databinding.ActivityMainBinding
import com.kyutae.jackpotluck.fragment.LogFragment
import com.kyutae.jackpotluck.fragment.MainFragment
import com.kyutae.jackpotluck.fragment.SettingFragment

class MainActivity : AppCompatActivity() {
    lateinit var bind: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)


        setCurrentFragment(MainFragment())

        // 네비게이션 아이템 선택 시 프래그먼트 변경
        bind.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_main -> setCurrentFragment(MainFragment())
                R.id.nav_log -> setCurrentFragment(LogFragment())
                R.id.nav_setting -> setCurrentFragment(SettingFragment())
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }

}