package com.example.tomatoleafdiseaseclassificationapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tomatoleafdiseaseclassificationapp.databinding.ActivityExploreBinding

class ExploreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExploreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}