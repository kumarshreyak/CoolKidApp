package com.mycompany.coolkidapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mycompany.coolkidapp.R
import com.mycompany.coolkidapp.SampleActivity
import com.mycompany.coolkidapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_home
        )
        initView()
    }

    private fun initView() {
        binding.coolBtn.setOnClickListener { startActivity(Intent(this, SampleActivity::class.java)) }
    }
}
