package com.example.tomatoleafdiseaseclassificationapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tomatoleafdiseaseclassificationapp.R
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentBioTabBinding
import com.example.tomatoleafdiseaseclassificationapp.databinding.FragmentConventionalTabBinding

class ConventionalTabFragment : Fragment() {
    private lateinit var fragmentConventionalTabBinding: FragmentConventionalTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentConventionalTabBinding = FragmentConventionalTabBinding.inflate(inflater, container, false)
        return fragmentConventionalTabBinding.root
    }
}