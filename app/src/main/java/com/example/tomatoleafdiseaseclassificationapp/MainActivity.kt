package com.example.tomatoleafdiseaseclassificationapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityCameraBinding
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.cameraIcon.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        activityMainBinding.toolbar.setNavigationOnClickListener {
            activityMainBinding.drawerLayoutMenu.open()
        }

        activityMainBinding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            activityMainBinding.drawerLayoutMenu.close()
            true
        }

        activityMainBinding.toolbar.setNavigationOnClickListener {
            activityMainBinding.drawerLayoutMenu.open()
        }

    }
}