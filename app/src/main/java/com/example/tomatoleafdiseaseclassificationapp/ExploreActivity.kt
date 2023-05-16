package com.example.tomatoleafdiseaseclassificationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityExploreBinding

class ExploreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExploreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}